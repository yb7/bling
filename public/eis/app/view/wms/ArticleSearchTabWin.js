Ext.define('Bling.view.wms.ArticleSearchTabWin', {
    extend: 'Ext.window.Window',
//    xtype: 'article-search-tab-win',
    requires: ['Bling.view.wms.ArticleSearchForm', 'Bling.view.wms.TallyArticleGrid'],

    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            title: '货品检索',
            maxWidth: 800,
            resizable: false,
            modal: true,
            items:[{
                xtype: 'tabpanel',
                items: [{
                    title: '搜索结果',
                    layout: 'anchor',
                    xtype: 'wms-article-search-form'
                }, {
                    width: 700,
                    title: '搜索结果',
                    xtype: 'tally-article-grid',
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
                        }
                    ]
                }]
            }]
        });
        me.callParent(arguments);
    }
});