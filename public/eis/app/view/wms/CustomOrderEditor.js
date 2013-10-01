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
            items: [
            {
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
                        xtype: 'textfield',
                        name: 'sellCode',
                        disabled:true,
                        fieldLabel: '预定单号'
                    },
                    {
                        xtype:'form',
                        layout:'column',
                        columnWidth:0.66,
                        defaults: {
                            margin:'5 5 5 5',
                            columnWidth:0.33
                        },
                        items: [
                            {
                                xtype: 'button',
                                itemId: 'cancelButton',
                                disabled: true,
                                text: '作废'
                            },
                            {
                                xtype: 'button',
                                itemId: 'saveButton',
                                disabled: true,
                                text: '保存/打印'
                            },
                            {
                                xtype: 'checkbox',
                                itemId: 'printCb',
                                boxLabel: '是否立即打印'
                            },
                        ]
                    }
                ]
            },
            {
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
                        xtype: 'hidden',
                        name: 'customCode',
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellStore',
                        fieldLabel: '门店名称'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellArea',
                        fieldLabel: '区域'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellBar',
                        fieldLabel: '柜台'
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
                        xtype: 'textfield',
                        name: 'customcolor',
                        fieldLabel: '颜色'
                    },
                    {
                        xtype: 'textfield',
                        name: 'custompure',
                        fieldLabel: '净度'
                    },
                    {
                        xtype: 'textfield',
                        name: 'customquality',
                        fieldLabel: '石重'
                    },
                    {
                        xtype: 'textfield',
                        name: 'customgoldgrade',
                        fieldLabel: '金料'
                    },
                    {
                        xtype: 'textfield',
                        name: 'customtotalquality',
                        fieldLabel: '总重'
                    },
                    {
                        xtype: 'textfield',
                        name: 'customsize',
                        fieldLabel: '指圈号'
                    },
                    {
                        xtype: 'datefield',
                        name: 'customDate',
                        format:'Y-m-d',
                        fieldLabel: '预定日期'
                    },
                    {
                        xtype: 'datefield',
                        name: 'expireDate',
                        format:'Y-m-d',
                        fieldLabel: '到期日期'
                    },
                    {
                        xtype:'panel',
                        height:24
                    },
                    {
                        xtype: 'numberfield',
                        name: 'totalAmount',
                        fieldLabel: '总金额'
                    },
                    {
                        xtype: 'numberfield',
                        name: 'earnestAmount',
                        fieldLabel: '定金'
                    }
                ]
            }]
        });

        me.callParent(arguments);
    }

});
