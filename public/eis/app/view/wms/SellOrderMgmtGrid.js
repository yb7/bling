Ext.define("Bling.view.wms.SellOrderMgmtGrid", {
    extend: 'Ext.panel.Panel',
    title: '销售单维护',
    xtype: 'sell-order-mgmt-grid',
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
                defaults: {
                    margin:'5 5 5 5',
                    labelWidth : 90,
                    labelAlign:'right',
                    columnWidth:0.33
                },
                items: [
                    {
                        xtype:'warehouse-combo',
                        fieldLabel:'门店名称',
                        name:'sellStore'
                    },
                    {
                        xtype:'datefield',
                        format:'Y-m-d',
                        fieldLabel:'起始日期',
                        name:'startdate'
                    },
                    {
                        xtype:'datefield',
                        format:'Y-m-d',
                        fieldLabel:"结束日期",
                        name:'enddate'
                    },
                    {
                        xtype:'textfield',
                        fieldLabel:'销售单号',
                        name:'sellOrder'
                    },
                    {
                        xtype:'textfield',
                        fieldLabel:'货品条码',
                        name:'goodsOrder'
                    },
                    {
                        xtype:'textfield',
                        fieldLabel:'客户联系方式',
                        name:'guestTel'
                    },
                ],
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
