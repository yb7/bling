Ext.define('Bling.view.wms.ArticleSearchTabWin', {
    extend: 'Ext.window.Window',
    xtype: 'article-search-tab-win',
    requires: ['Bling.view.wms.ArticleSearchForm',
        'Bling.view.wms.TallyArticleGrid',
        'Ext.data.Store',
        'Ext.toolbar.Paging',
        'Ext.selection.CheckboxModel'],

    initComponent: function() {
        var me = this;
        var store = Ext.create('Ext.data.Store', {
            model: 'Bling.model.article.Article',
            pageSize: 13
        });
        Ext.apply(me, {
            title: '货品检索',
            maxWidth: 800,
            resizable: false,
            modal: true,
            items:[{
                xtype: 'tabpanel',
                items: [{
                    title: '搜索信息',
                    layout: 'anchor',
                    xtype: 'wms-article-search-form',
                    width: 750
                }, {
                    width: 750,
                    title: '搜索结果',
                    xtype: 'tally-article-grid',
                    selModel:Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI'}),
                    height: 445,
                    store: store,
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            ui: 'footer',
                            items: [ '->',
                                {
                                    xtype: 'button',
                                    itemId: 'addArticlesBtn',
                                    text: '添加货品'
                                }
                            ]
                        }, {
                            xtype: 'pagingtoolbar',
                            dock: 'bottom',
                            ui: 'footer',
                            store: store,   // same store GridPanel is using
                            displayInfo: true
                        }
                    ]
                }]
            }]
        });
        me.callParent(arguments);
    }
});