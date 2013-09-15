Ext.define('Bling.controller.wms.ArticleSearchTabWin', {
    extend: 'Ext.app.Controller',


    init: function() {
        var me = this;
        this.control({
            'article-search-tab-win tabpanel': {
                tabchange: this.doSearch
            },
            'article-search-tab-win #addArticlesBtn': {
                click: this.addArticles
            }
        })
    },

    generateQuery: function(values) {
        for (var i in values) {
            if (!values[i])
                delete values[i];
        }
        values.inTransitOrStorage = 'Storage';
        return Ext.urlEncode(values);
    },

    doSearch: function(tabPanel, newCard, oldCard, eOpts){

        if (newCard.title == '搜索结果') {
            var form = tabPanel.down('wms-article-search-form').getForm();
            var queryString = this.generateQuery(form.getFieldValues());
            if (!form.isValid() || Ext.isEmpty(queryString)) {
                Ext.MessageBox.alert('警告', '请先正确填写检索条件');
                return;
            }
            var store = tabPanel.down('tally-article-grid').store;
            store.setProxy(new Ext.data.proxy.Ajax({
                url:'/eis/article/articles?' + queryString,
                reader:{
                    type: 'json',
                    root:'data'
                }
            }));
            store.loadPage(1);

        }
    },
    addArticles: function(btn) {
        var selections = btn.up('tally-article-grid').getSelectionModel().getSelection();
        var array = [];
        Ext.each(selections, function(model, index, selectionsItself){
            array.push(model.data.id);
        });
        btn.up('article-search-tab-win').fireEvent('add-articles', array);
    }
});
