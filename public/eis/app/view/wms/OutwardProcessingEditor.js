Ext.define('Bling.view.wms.OutwardProcessingEditor', {
    extend: 'Ext.panel.Panel',
    xtype: 'outward-processing-editor',
    requires: ['Bling.view.wms.TallyArticleGrid'],

    bodyPadding: 10,
    title: '外发维护',
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
                xtype: 'red-or-blue-combo',
                fieldLabel: '红蓝字',
                name: 'readOrBlue'
            }, {
                xtype: 'textfield',
                labelWidth:85,
                fieldLabel: '外发单号',
                name: 'bizCode',
                disabled: true
            }, {
                xtype:'warehouse-combo',
                fieldLabel:'接收区域',
                name:'receiveWarehouseId'
            }, {
                xtype:'datefield',
                fieldLabel:'外发日期',
                format:'Y-m-d',
                name:'outwardDate'
            }, {
                xtype:'datefield',
                fieldLabel:'预期完成日期',
                labelWidth:85,
                format:'Y-m-d',
                name:'expectedCompletionDate'
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
                            text: '执行外发'
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