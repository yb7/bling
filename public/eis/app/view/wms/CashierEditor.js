Ext.define('Bling.view.wms.CashierEditor', {
    extend: 'Ext.panel.Panel',
    xtype: 'cashier-editor',

    requires: ['Ext.form.field.Hidden', 'Ext.form.field.File', 'Bling.model.article.Article',
        'Bling.view.shared.WarehouseCombo'],

    bodyPadding: 10,
    title: '收银单编辑',
    closable:true,

    initComponent: function() {
        var me = this;

        var grid = {
            xtype: 'grid',
            store: 'wms.ReceivingOrders',
            columns: [
                {text: 'ID', dataIndex: 'id', hidden: true, width: 50},
                {text: '货品条码', dataIndex: 'goodsCode', width: 150},
								{text: '数量', dataIndex:'count', width:150},
                {text: '货品名称', dataIndex: 'goodsName', width:150},
                {text: '货品品种', dataIndex: 'goodsCategory', width:150},
								{text: '金单价', dataIndex: 'goldPrice',width:150},
								{text: '货品计价方式', dataIndex:'accountPrice',width:150},
                {text: '货品重量', dataIndex: 'goodsWeight', width:150},
                {text: '工费金额', dataIndex: 'humanPrice', width:150},
                {text: '销售原价', dataIndex: 'sellPrice', width:150},
                {text: '折扣率', dataIndex: 'discountRate', width:150},
                {text: '折扣金额', dataIndex: 'discountAmount', width:150},
                {text: '抵值金额', dataIndex: 'dischargeAmount', width:150},
                {text: '样式', dataIndex: 'style', width:150},
                {text: '批号', dataIndex: 'serialCode', width:150},
                {text: '款号', dataIndex: 'styleCode', width:150},
                {text: '手寸', dataIndex: 'size', width:150},
                {text: '金属名称', dataIndex: 'metalName', width:150},
                {text: '金属纯度', dataIndex: 'metalPure', width:150},
                {text: '证书重量', dataIndex: 'credentialsWeight', width:150},
                {text: '标签名称', dataIndex: 'tagName', width:150},
                {text: '证书号', dataIndex: 'credentialCode', width:150},
                {text: '货品备注', dataIndex: 'goodsComments', width:150},
                {text: '货品备注', dataIndex: 'goodsComments', width:150},
                {text: '金重量', dataIndex: 'metalWeight', width:150},
                {text: '镶口', dataIndex: 'rabbet', width:150},
                {text: '宝石名称', dataIndex: 'jewelName', width:150},
                {text: '宝石石号', dataIndex: 'jewelStoneCode', width:150},
                {text: '宝石颜色', dataIndex: 'jewelColor', width:150},
                {text: '宝石净度', dataIndex: 'jewelPure', width:150},
                {text: '宝石切工', dataIndex: 'jewelIncision', width:150},
                {text: '宝石数量', dataIndex: 'jewelCount', width:150},
                {text: '宝石重量', dataIndex: 'jewelWeight', width:150},
                {text: '宝石备注', dataIndex: 'jewelComments', width:150},
                {text: '辅石名称', dataIndex: 'sidestoneName', width:150},
                {text: '辅石数量', dataIndex: 'sidestoneCount', width:150},
                {text: '辅石重量', dataIndex: 'sidestoneWeight', width:150},
                {text: '辅石石号', dataIndex: 'sidestoneCode', width:150}
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
                    columnWidth: 0.25
                },
                fieldDefaults: {
                    labelWidth:70,
                    labelAlign:'right'
                },
                items: [
                    {
                        xtype: 'textfield',
                        name: 'cashOrder',
                        fieldLabel: '收银单号'
                    },
                    {
                        xtype: 'textfield',
                        name: 'sellStore',
												disabled:true,
                        fieldLabel: '门店名称'
                    },
										{
										    xtype:'combo',
												name:'seller1',
												fieldLabel:'营业员1'
										},
										{
										    xtype:'combo',
												name:'seller2',
												fieldLabel:'营业员2'
										},
										{
										    xtype:'combo',
												name:'sellArea',
												fieldLabel:'区域'
										},
										{
										    xtype:'datefield',
												name:'sellDate',
												fieldLabel:'销售日期'
										},
										{
										    xtype:'textfield',
												name:'memberCode',
												fieldLabel:'会员编号'
										},
										{
										    xtype:'combo',
												name:'sellBar',
												fieldLabel:'柜台'
										},
										{
										    xtype:'textfield',
												name:'consumerName',
												fieldLabel:'顾客姓名'
										},
										{
										    xtype:'textfield',
												name:'mobile',
												fieldLabel:'联系电话'
										},
										{
										    xtype:'combo',
												name:'sellRound',
												fieldLabel:'班次'
										},
                ]
            },
            {
                xtype: 'fieldset',
                layout:'column',
								title:'开票信息',
                defaults: {
                    margin:'5 5 5 5',
                    columnWidth: 0.25
                },
                fieldDefaults: {
                    labelWidth:70,
                    labelAlign:'right'
                },
                items: [
									{
										xtype:'textfield',
										name:'invoiceCode',
										fieldLabel:'发票号码'
									},
									{
										xtype:'textfield',
										name:'invoiceAmount',
										fieldLabel:'开票金额'
									}
                ]
            }, 
						{
								xtype:'fieldset',
								title:'支付',
								layout:'column',
                defaults: {
                    margin:'5 5 5 5',
                    columnWidth: 0.25
                },
                fieldDefaults: {
                    labelWidth:120,
                    labelAlign:'right'
                },
								items: [
									{
										xtype:'textfield',
										name:'realPrice',
										fieldLabel:'实收金额'
									},
									{
										xtype:'textfield',
										name:'preOrderCode',
										disabled:true,
										fieldLabel:'预定单号'
									},
									{
										xtype:'textfield',
										name:'earnestAmount',
										disabled:true,
										fieldLabel:'定金金额'
									},
									{
										xtype:'textfield',
										name:'dischargeAmount',
										disabled:true,
										fieldLabel:'抵值金'
									},
									{
										xtype:'textfield',
										name:'couponAmount',
										disabled:true,
										fieldLabel:'抵用券金额'
									},
									{
										xtype:'textfield',
										name:'couponCard1',
										fieldLabel:'卡片卡号1'
									},
									{
										xtype:'textfield',
										name:'couponCard2',
										fieldLabel:'卡片卡号2'
									},
									{
										xtype:'textfield',
										name:'giftCard',
										fieldLabel:'礼金卡号'
									},
									{
										xtype:'panel',
										border:false,
										height:10,
										columnWidth:1,
										html:'<hr width="100%">'
									},
									{
										xtype:'textfield',
										name:'bankNBPosPayment',
										fieldLabel:'宁波POS刷卡金额'
									},
									{
										xtype:'textfield',
										name:'tonglianPayment',
										fieldLabel:'通联支付金额'
									},
									{
										xtype:'textfield',
										name:'accountTrans',
										fieldLabel:'财务转账'
									},
									{
										xtype:'textfield',
										name:'alipayPayment',
										fieldLabel:'支付宝'
									},
									{
										xtype:'textfield',
										name:'ICBCPosPayment',
										fieldLabel:'工行POS刷卡金额'
									},
									{
										xtype:'textfield',
										name:'tonglianPayment2',
										fieldLabel:'通联2支付金额'
									},
									{
										xtype:'textfield',
										name:'accountRefund',
										fieldLabel:'财务退款'
									},
									{
										xtype:'textfield',
										name:'giftCardPayment',
										fieldLabel:'礼金卡'
									},
									{
										xtype:'textfield',
										name:'CEBPosPayment',
										fieldLabel:'光大POS刷卡金额'
									},
									{
										xtype:'textfield',
										name:'BCMPosPayment',
										fieldLabel:'交行POS刷卡金额'
									},
									{
										xtype:'textfield',
										name:'installmentPayment',
										fieldLabel:'分期刷卡'
									},
									{
										xtype:'textfield',
										name:'consumeCardPayment',
										fieldLabel:'消费卡'
									},
									{
										xtype:'textfield',
										name:'BCPosPayment',
										fieldLabel:'中行POS刷卡金额'
									}
								]
						},grid]
        });

        me.callParent(arguments);
    }

});
