Ext.define("Bling.view.wms.ReceivingOrderMgmtGrid", {
    extend: 'Ext.panel.Panel',
    title: '入库单管理',
    xtype: 'receiving-order-mgmt-grid',
    requires: [
        'Ext.toolbar.TextItem',
        'Ext.form.field.Checkbox',
        'Ext.form.field.Text',
        'Ext.ux.statusbar.StatusBar',
        'Ext.form.Panel',
        'Ext.layout.container.Column'
    ],


    // Component initialization override: adds the top and bottom toolbars and setup headers renderer.
    initComponent: function() {
        var me = this;
        var grid = {
            xtype: 'grid',
            store: 'wms.ReceivingOrders',
            columns: [
                {text: 'ID', dataIndex: 'id', hidden: true},
                {text: '入库单号', dataIndex: 'bizCode'},
                {text: '入库时间', dataIndex: 'receivingTime'}
            ],
            flex: 1
        };
        var config = {
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [{
                xtype: 'form',
                layout : 'column',
                height:70,
                defaults: {
                    labelWidth : 70,
                    labelAlign:'right',
                    columnWidth:0.25
                },
                items: [{
                    xtype:'textfield',
                    fieldLabel:'入库单号',
                    name:'recCode',
                    labelWidth:70
                }],
                buttons:[{
                    text: '查询',
                    itemId: 'searchBtn'
                }, {
                    text: '新增',
                    itemId: 'createBtn'
                }]

            }, grid]
        };

        Ext.apply(me, config);
        me.callParent(arguments);
    }
});