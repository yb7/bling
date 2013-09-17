Ext.define("Bling.view.wms.PriceAdjustmentGrid", {
    extend: 'Ext.panel.Panel',
    title: '调价',
    xtype: 'price-adjustment-grid',
    requires: [
        'Ext.ux.statusbar.StatusBar',
        'Ext.form.Panel',
        'Ext.layout.container.Column',
        'Ext.form.field.Date',
        'Ext.form.field.ComboBox',
        'Ext.layout.container.Table'
    ],


    initComponent: function() {
        var me = this;
        var grid = {
            xtype: 'grid',
            flex:1,
            store: 'wms.PriceAdjustments',
            columns: [
                {text: 'ID', dataIndex: 'id', hidden: true, width: 50},
                {text: '调价单号', dataIndex: 'bizCode', width: 150},
                {text: '生效日期', dataIndex: 'effectiveDate', width: 150, xtype: 'datecolumn', format:'Y-m-d'},
                {text: '指定零售价', dataIndex: 'retailPriceDirectly', width: 150},

                {text: '成本增量', dataIndex: 'incrementBaseOnCost', width: 150},
                {text: '成本系数', dataIndex: 'coefficientBaseOnCost', width: 150},

                {text: '零售价增量', dataIndex: 'incrementBaseOnRetailPrice', width: 150},
                {text: '零售价系数', dataIndex: 'coefficientBaseOnRetailPrice', width: 150},

                {text: '个位不包含', dataIndex: 'excludeUnits', width: 200},
                {text: '备注', dataIndex: 'remark', flex:1}
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    ui: 'footer',
                    items: [ '->',{
                            xtype: 'button',
                            text: '查询',
                            itemId: 'searchBtn'
                        }, '-', {
                        xtype: 'button',
                            itemId: 'createBtn',
                            text: '新建'
                        }
                    ]
                }
            ]
        };
        var config = {
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [{
                xtype: 'form',
                layout: 'column',
                defaults:{
                    margin:'5 5 5 5',
                    columnWidth: 0.33
                },
                fieldDefaults: {
                    labelWidth: 60
                },

                bodyPadding: 10,
                items: [{
                    xtype:'datefield',
                    fieldLabel:'外发日期',
                    name:'allocationDate'
                }, {
                    xtype:'warehouse-combo',
                    fieldLabel:'接收区域',
                    name:'receiveRegion'
                }]
            }, grid]
        };

        Ext.apply(me, config);
        me.callParent(arguments);
    }
});