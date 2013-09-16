Ext.define('Bling.controller.wms.PriceAdjustmentEditor', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.wms.ArticleSearchTabWin'],
    views: ['wms.ArticleSearchTabWin'],
    refs: [
        {
            selector: 'content-panel',
            ref: 'contentPanel'
        }
    ],


    init: function() {
        var me = this;
        this.control({
            'price-adjustment-editor #addArticlesBtn': {
                click: this.openAddArticlesWin
            },
            'price-adjustment-editor tally-article-grid': {
                afterrender: function(cmp) {
                    cmp.getStore().load();
                },
                selectionchange: function(selModel, selections, opts) {
                    this.getContentPanel().getActiveTab().down('#deleteBtn').setDisabled(selections.length === 0);
                }
            },
            'price-adjustment-editor #deleteBtn': {
                click: this.deleteArticle
            },
            'price-adjustment-editor #executeBtn': {
                click: this.execute
            }
        })
    },
    deleteArticle: function(cmp){
        var grid = cmp.up('tally-article-grid');
        var selected = grid.getSelectionModel().getSelection()[0];
        if (selected) {
            grid.getStore().remove(selected);
        }
    },
    openAddArticlesWin: function(cmp) {
        var me = this;
        var orderId = this.getOrderId(cmp);

        var win = this.getView('wms.ArticleSearchTabWin').create();
        win.on('add-articles', function(array) {
            Ext.Ajax.request({
                url: '/eis/wms/price-adjustments/' + orderId + '/articles',
                method:'POST',
                jsonData:array,
                failure:function(response){
                    var e = Ext.decode(response.responseText);
                    Ext.Msg.alert('警告', e.message);
                }
            });
        });

        var articleStore = cmp.up('tally-article-grid').getStore();
        win.on('close', function(){
            articleStore.load();
        });

        win.show();
    },
    getOrderId: function(cmp) {
        return this.getEditor(cmp).down('form hidden[name=id]').getValue();
    },
    getEditor: function(cmp) {
        return cmp.up('price-adjustment-editor');
    },
    execute: function(btn) {
        var me = this;
        var id = this.getOrderId(btn);
        var executeUrl = '/eis/wms/price-adjustments/' + id + '/execute';
        var formValues = this.getEditor(btn).down('form').getForm().getValues();
        delete formValues.bizCode;
        delete formValues.id;
        Ext.Ajax.request({
            url:executeUrl,
            method:'PUT',
            jsonData:formValues,
            success:function (response) {
                me.getEditor(btn).close();
                Ext.data.StoreManager.lookup('wms.PriceAdjustments').load();
            },
            failure:function(response){
                var e = Ext.decode(response.responseText);
                Ext.Msg.alert('警告', e.message);
            }
        });
    }
});
