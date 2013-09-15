Ext.define('Bling.view.wms.RegionalAllocationEditor', {
    extend: 'Ext.panel.Panel',
    xtype: 'regional-allocation-editor',
    requires: ['Bling.view.wms.TallyArticleGrid'],

    bodyPadding: 10,
    title: '区域调拨新增',
    closable:true,

    initComponent: function() {
        var me = this;

        var form = {
            xtype: 'form',
            layout: {
                type: 'column'
            },
            defaults:{
                margin:'5 5 5 5',
                columnWidth: 0.33
            },
            fieldDefaults:{
                labelWidth:60,
                labelAlign:'left'
            },
            items: [{
                xtype: 'textfield',
                fieldLabel: '调拨单号',
                name: 'bizCode',
                disabled: true
            }, {
                xtype:'datefield',
                fieldLabel:'调拨日期',
                format:'Y-m-d',
                name:'allocationDate'
            }, {
                xtype:'warehouse-combo',
                fieldLabel:'接收区域',
                name:'receiveWarehouseId'
            }, {
                xtype: 'textarea',
                fieldLabel: '备注',
                name: 'remark',
                columnWidth: 1
            },{
                xtype:'hidden',
                name:'id'
            }, {
                xtype:'hidden',
                name:'version'
            }]
        };
        var grid = {
            xtype: 'tally-article-grid',
            flex:1,
            store: me.articleStore,
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
                        }, {
                            xtype: 'button',
                            itemId: 'deleteBtn',
                            disabled: true,
                            text: '删除'
                        }, '-', {
                            xtype: 'button',
                            itemId: 'executeBtn',
                            text: '执行调拨'
                        }
                    ]
                }
            ]
        }

        Ext.apply(me, {
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [form, grid]
        });

        me.callParent(arguments);
    }

});