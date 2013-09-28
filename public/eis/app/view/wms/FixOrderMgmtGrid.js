Ext.define("Bling.view.wms.FixOrderMgmtGrid", {
    extend: 'Ext.panel.Panel',
    title: '销售单维护',
    xtype: 'fix-order-mgmt-grid',
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
                {text: 'ID', dataIndex: 'id', hidden: true, width: 50},
                {text: '销售单号', dataIndex: 'sellCode', width: 150},
                {text: '货品条码', dataIndex: 'goodsCode', width: 150},
                {text: '货品名称', dataIndex: 'goodsName', width:150},
                {text: '货品品种', dataIndex: 'goodsCategory', width:150},
                {text: '货品重量', dataIndex: 'goodsWeight', width:150},
                {text: '金属名称', dataIndex: 'metalName', width:150},
                {text: '金重', dataIndex: 'metalWeight', width:150},
                {text: '宝石名称', dataIndex: 'jewelName', width:150}
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
                    name:'sellCode',
                    labelWidth:70
                }],
                buttons:[{
                    text: '查询',
                    itemId: 'searchSellBtn'
                }, {
                    text: '新增',
                    itemId: 'createSellBtn'
                }]

            }, grid]
        };

        Ext.apply(me, config);
        me.callParent(arguments);
    }
});
