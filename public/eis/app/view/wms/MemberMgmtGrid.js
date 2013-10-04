Ext.define("Bling.view.wms.MemberMgmtGrid", {
    extend: 'Ext.panel.Panel',
    title: '会员资料',
    xtype: 'member-mgmt-grid',
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
                {text: '会员卡号', dataIndex: 'memberCardCode', width: 150},
                {text: '总积分', dataIndex: 'memberPoint', width: 150},
                {text: '客户姓名', dataIndex: 'memberName', width:150},
                {text: '移动电话', dataIndex: 'memberMobile', width:150},
                {text: '固定电话', dataIndex: 'memberTel', width:150},
                {text: '住址', dataIndex: 'memberAddress', width:150},
                {text: 'QQ', dataIndex: 'memberQQ', width:150}
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
                    columnWidth:0.25
                },
                items: [
                    {
                        xtype:'textfield',
                        fieldLabel:'会员卡号',
                        name:'memberCardCode'
                    },
                    {
                        xtype:'textfield',
                        fieldLabel:'客户姓名',
                        name:'memberName'
                    },
                    {
                        xtype:'textfield',
                        fieldLabel:"移动电话",
                        name:'memberMobile'
                    },
                    {
                        xtype:'textfield',
                        fieldLabel:'身份证',
                        name:'memberId'
                    }
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
