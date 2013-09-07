package eis.domain.model.article

import javax.persistence.{ManyToOne, Table, Entity}
import com.wyb7.waffle.domain.entity.IdVersionEntity

/**
 * User: abin
 * Date: 13-9-7
 * Time: 下午10:08
 */

@Entity
@Table(name = "article_category")
class ArticleCategory extends IdVersionEntity {
    var name: String = _
    @ManyToOne
    var parent: ArticleCategory = _
}
