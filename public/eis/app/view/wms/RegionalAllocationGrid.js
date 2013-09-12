Ext.define("Bling.view.wms.RegionalAllocationGrid", {
    extend: 'Ext.panel.Panel',
    title: '区域调拨',
    xtype: 'regional-allocation-grid',
    requires: [
        'Ext.ux.statusbar.StatusBar',
        'Ext.form.Panel',
        'Ext.layout.container.Column',
        'Ext.form.field.Date',
        'Ext.form.field.ComboBox',
        'Ext.layout.container.Table'
    ],


//    bodyPadding: 10,

    // Component initialization override: adds the top and bottom toolbars and setup headers renderer.
    initComponent: function() {
        var me = this;
        var grid = {
            xtype: 'grid',
            flex:1,
            columns: [
                {text: 'ID', dataIndex: 'id', hidden: true},
                {text: '调拨单号'},
                {text: '制单人'},
                {text: '调拨日期'},
                {text: '发出区域'},
                {text: '接收区域'}
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    ui: 'footer',
                    items: [ '->',
                        {
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
                layout: {
                    type: 'hbox',
                    align: 'stretch',
                    defaultMargins: {right: 5, left: 5}
                },

                bodyPadding: 10,
                items: [{
                    xtype:'datefield',
                    fieldLabel:'调拨日期',
                    name:'allocationDate',
                    labelWidth: 70,
                    flex: 1
                }, {
                    xtype:'combo',
                    fieldLabel:'发出区域',
                    name:'sendRegion',
                    labelWidth: 70,
                    flex: 1
                }, {
                    xtype:'datefield',
                    fieldLabel:'接收区域',
                    name:'receiveRegion',
                    labelWidth: 70,
                    flex: 1
                }, {
                    xtype: 'button',
                    text: '查询',
                    itemId: 'searchBtn',
                    flex: 1
                }]
            }, grid]
        };

        Ext.apply(me, config);
        me.callParent(arguments);
    }
});