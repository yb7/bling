Ext.define('Bling.view.wms.MemberEditor', {
    extend: 'Ext.panel.Panel',
    xtype: 'member-editor',

    requires: ['Ext.form.field.Hidden', 'Ext.form.field.File', 'Bling.model.article.Article',
        'Bling.view.shared.WarehouseCombo'],

    bodyPadding: 10,
    title: '会员新增',
    closable:true,

    initComponent: function() {
        var me = this;

        var grid = {
            xtype: 'grid',
            store: 'wms.ReceivingOrders',
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                ui:'footer',
                items: ['->', {
                        xtype: 'button',
                        itemId: 'deleteBtn',
                        disabled: true,
                        text: '删除'
                    }
                ]
            }],
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
                    labelAlign:'right'
                },
                items: [
                    {
                        xtype: 'textfield',
                        name: 'memberCardCode',
                        fieldLabel: '会员卡号'
                    },
                    {
                        xtype: 'textfield',
                        name: 'memberName',
                        fieldLabel: '会员名称'
                    },
                    {
                        xtype: 'textfield',
                        name: 'memberMobile',
                        fieldLabel: '移动电话'
                    },
                    {
                        xtype: 'textfield',
                        name: 'memberSex',
                        fieldLabel: '性别'
                    },
                    {
                        xtype: 'textfield',
                        name: 'memberTel',
                        fieldLabel: '固定电话'
                    },
                    {
                        xtype: 'datefield',
												format:'Y-m-d',
                        name: 'memberBirthdate',
                        fieldLabel: '生日'
                    },
                    {
                        xtype: 'textfield',
                        name: 'memberQQ',
                        fieldLabel: 'QQ'
                    },
                    {
                        xtype: 'textfield',
                        name: 'memberId',
                        fieldLabel: '身份证'
                    },
                    {
                        xtype: 'textfield',
                        name: 'memberEmail',
                        fieldLabel: 'Email'
                    },
                    {
                        xtype: 'textfield',
                        name: 'memberPoint',
												disabled:true,
                        fieldLabel: '总积分'
                    },
                    {
                        xtype: 'textfield',
                        name: 'memberAddress',
                        fieldLabel: '住址',
												columnWidth : 0.66
                    },
                    {
                        xtype: 'textfield',
                        name: 'comments',
                        fieldLabel: '备注',
												columnWidth : 0.99
                    },
										{
												xtype:'panel',
												height:24
										},
										{
												xtype:'panel',
												height:24,
												columnWidth:0.41
										},
										{
												align:'right',
										    xtype: 'button',
												itemId: 'saveButton',
												text:'保存',
												columnWidth:0.25
										}
                ]
            }]
        });

        me.callParent(arguments);
    }

});
