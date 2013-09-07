package util

import org.springframework.core.io.support.{ResourcePatternUtils, PathMatchingResourcePatternResolver, ResourcePatternResolver}
import org.springframework.core.io.ResourceLoader
import org.springframework.util.ClassUtils
import org.springframework.core.`type`.classreading.{MetadataReader, CachingMetadataReaderFactory, MetadataReaderFactory}
import java.io.IOException
import org.hibernate.MappingException
import org.springframework.core.`type`.filter.{AnnotationTypeFilter, TypeFilter}
import javax.persistence.{MappedSuperclass, Embeddable, Entity}

/**
 * Author: Wang Yibin
 * Date: 13-1-20
 * Time: 下午6:17
 */
object EntityClassScanner {
    private val resourcePatternResolver: ResourcePatternResolver = {
        val defaultResourceLoader: ResourceLoader = new PathMatchingResourcePatternResolver()
        ResourcePatternUtils.getResourcePatternResolver(defaultResourceLoader)
    }

    def scanPackages(packagesToScan: String*) = {
        try {
            val entityClazz = for (pkg <- packagesToScan) yield {
                val pattern: String = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN
                val resources = resourcePatternResolver.getResources(pattern).toList
                val readerFactory: MetadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver)

                for {resource <- resources
                     if resource.isReadable
                     reader = readerFactory.getMetadataReader(resource)
                     if matchesFilter(reader, readerFactory)
                } yield {
                    val className = reader.getClassMetadata.getClassName
                    resourcePatternResolver.getClassLoader.loadClass(className)
                }
            }
            entityClazz.flatten.toList
        }
        catch {
            case ex: IOException => {
                throw new MappingException("Failed to scan classpath for unlisted classes", ex)
            }
            case ex: ClassNotFoundException => {
                throw new MappingException("Failed to load annotated classes from classpath", ex)
            }
        }
    }

    /**
     * Check whether any of the configured entity type filters matches
     * the current class descriptor contained in the metadata reader.
     */
    private def matchesFilter(reader: MetadataReader, readerFactory: MetadataReaderFactory): Boolean = {
        for (filter <- ENTITY_TYPE_FILTERS) {
            if (filter.`match`(reader, readerFactory)) {
                return true
            }
        }
        return false
    }

    private final val ENTITY_TYPE_FILTERS: Array[TypeFilter] = Array(
        new AnnotationTypeFilter(classOf[Entity], false),
        new AnnotationTypeFilter(classOf[Embeddable], false),
        new AnnotationTypeFilter(classOf[MappedSuperclass], false)
    )
    private final val RESOURCE_PATTERN: String = "/**/*.class"
}
