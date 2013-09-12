Ext.define('Bling.view.wms.RegionalAllocationCreator', {
    extend: 'Ext.panel.Panel',
    xtype: 'regional-allocation-creator',
    requires: ['Bling.view.wms.TallyArticleGrid'],

    bodyPadding: 10,
    title: '区域调拨新增',

    initComponent: function() {
        var me = this;

        var form = {
            xtype: 'form',
            layout: {
                type: 'column'
            },
            defaults:{
                margin:'5 5 5 5',
                columnWidth: 0.25
            },
            fieldDefaults:{
                labelWidth:60,
                labelAlign:'left'
            },
            items: [{
                xtype: 'textfield',
                fieldLabel: '调拨单号',
                name: 'bizCode',
                columnWidth: 0.25
            }, {
                xtype:'datefield',
                fieldLabel:'调拨日期',
                name:'allocationDate',
                columnWidth: 0.25
            }, {
                xtype:'combo',
                fieldLabel:'发出区域',
                name:'sendRegion',
                columnWidth: 0.25
            }, {
                xtype:'datefield',
                fieldLabel:'接收区域',
                name:'receiveRegion',
                columnWidth: 0.25
            }, {
                xtype: 'textarea',
                fieldLabel: '备注',
                itemId: 'remark',
                columnWidth: 1
            }]
        };
        var grid = {
            xtype: 'tally-article-grid',
            flex:1,
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