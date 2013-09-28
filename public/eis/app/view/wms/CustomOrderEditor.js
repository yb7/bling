Ext.define('Bling.view.wms.CustomOrderEditor', {
    extend: 'Ext.panel.Panel',
    xtype: 'custom-order-editor',

    requires: ['Ext.form.field.Hidden', 'Ext.form.field.File', 'Bling.model.article.Article',
        'Bling.view.shared.WarehouseCombo'],

    bodyPadding: 10,
    title: '定制新增',
    closable:true,

    initComponent: function() {
        var me = this;

        Ext.apply(me, {
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [{
                xtype: 'form',
                layout:'column',
                defaults: {
                    margin:'5 5 5 5',
                    columnWidth: 0.33
                },
                fieldDefaults: {
                    labelWidth:70,
                    labelAlign:'left'
                },
                items: [
                    {
                        xtype:'hidden',
                        name:'id'
                    },
                    {
                        xtype:'hidden',
                        name:'version'
                    },
                    {
                        xtype: 'textfield',
                        name: 'sellCode',
                        fieldLabel: '入库单号'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellStore',
                        fieldLabel: '销售门店'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellArea',
                        fieldLabel: '销售区域'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellBar',
                        fieldLabel: '销售柜台'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'seller1',
                        fieldLabel: '营业员1'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'seller2',
                        fieldLabel: '营业员2'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellRound',
                        fieldLabel: '班次'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellPlan',
                        fieldLabel: '营销方案'
                    },
                    {
                        xtype: 'datefield',
                        name: 'sellDate',
                        format:'Y-m-d',
                        fieldLabel: '销售日期'
                    },
                    {
                        xtype: 'textfield',
                        name: 'orderCode',
                        fieldLabel: '预定单号'
                    },
                    {
                        xtype: 'textfield',
                        name: 'goodsCode',
                        fieldLabel: '条码扫描'
                    }
                ]
            }, {
                xtype: 'tally-article-grid',
                flex: 1,
                store: me.articleStore,
                dockedItems: [{
                    xtype: 'toolbar',
                    dock: 'top',
                    ui:'footer',
                    items: ['->', {
                            xtype: 'button',
                            itemId: 'deleteBtn',
                            disabled: true,
                            text: '删除'
                        }, '-', {
                            text: '执行入库',
                            itemId: 'executeBtn'
                        }
                    ]
                }]
            }]
        });

        me.callParent(arguments);
    }

});
