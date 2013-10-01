Ext.define('Bling.view.wms.FixOrderEditor', {
    extend: 'Ext.panel.Panel',
    xtype: 'fix-order-editor',

    requires: ['Ext.form.field.Hidden', 'Ext.form.field.File', 'Bling.model.article.Article',
        'Bling.view.shared.WarehouseCombo'],

    bodyPadding: 10,
    title: '维修新增',
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
                        name: 'sellCode',
                        fieldLabel: '预定单号'
                    },
                    {
                        xtype: 'warehouse-combo',
                        name: 'sellStore',
                        fieldLabel: '销售门店'
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
                        name: 'goodsCode',
                        disabled:true,
                        fieldLabel: '条码扫描',
                        columnWidth:0.66
                    },
                    {
                        xtype: 'datefield',
                        name: 'fixDate',
                        format:'Y-m-d',
                        fieldLabel: '维修日期'
                    },
                    {
                        xtype: 'datefield',
                        name: 'doneDate',
                        format:'Y-m-d',
                        fieldLabel: '预计完成日期'
                    },
                    {
                        xtype:'panel',
                        height:24
                    },
                    {
                        xtype: 'textfield',
                        name: 'fixPrice',
                        disabled:true,
                        fieldLabel: '金额'
                    }
                ]
            },
            {
                xtype:'panel',
                border:false,
                height:10,
                html:'<hr width="100%">'
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
                    labelAlign:'right'
                },
                items: [
                    {
                        xtype: 'textfield',
                        name: 'fixOrderColor',
                        fieldLabel: '颜色'
                    },
                    {
                        xtype: 'textfield',
                        name: 'fixOrderPure',
                        fieldLabel: '净度'
                    },
                    {
                        xtype: 'textfield',
                        name: 'fixOrderStoneQuality',
                        fieldLabel: '石重'
                    },
                    {
                        xtype: 'textfield',
                        name: 'fixOrderGoldGrade',
                        fieldLabel: '金料'
                    },
                    {
                        xtype: 'textfield',
                        name: 'fixOrderTotalQuality',
                        fieldLabel: '总重'
                    },
                    {
                        xtype: 'textfield',
                        name: 'fixOrderFingerSize',
                        fieldLabel: '指圈号'
                    }
                ]
            }]
        });

        me.callParent(arguments);
    }

});
