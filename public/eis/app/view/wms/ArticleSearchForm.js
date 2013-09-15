Ext.define('Bling.view.wms.ArticleSearchForm', {
    extend: 'Ext.form.Panel',
    xtype: 'wms-article-search-form',
    requires: ['Ext.layout.container.Column'],



    initComponent: function() {
        var me = this;

        Ext.apply(me, {
            title: '货品搜索',
            layout:'column',
            defaults: {
                xtype: 'textfield',
                margin:'5 5 5 5',
                columnWidth: 0.33
            },
            fieldDefaults: {
                labelWidth:70,
                labelAlign:'left'
            },
            items: [
                { name: 'sourceBizType', fieldLabel: '源单类型', xtype:'source-biz-type-combo' },
                { name: 'sourceBizCode',  fieldLabel: '源单单号', xtype:'source-biz-type-combo' },
                { name: 'warehouseId', fieldLabel: '仓库',  xtype: 'warehouse-combo'},
                { name: 'code', fieldLabel: '商品编码' },
                { name: 'vendor1', fieldLabel: '供应商1', xtype:'company-combo' },
                { name: 'vendor2', fieldLabel: '供应商2', xtype:'company-combo' },
                { name: 'vendor3', fieldLabel: '供应商3', xtype:'company-combo' },
                { name: 'vendorArticleName', fieldLabel: '厂家品名' },
                { name: 'vendorArticleNumber', fieldLabel: '厂家货号' },
                { name: 'name', fieldLabel: '货品名称' },
                { name: 'category', fieldLabel: '货品品种' },
                { name: 'style', fieldLabel: '样式' },
                { name: 'lotNumber', fieldLabel: '批号' },
                { name: 'styleNumber', fieldLabel: '款号' },
                { name: 'handInch', fieldLabel: '手寸' },
                { name: 'metalName', fieldLabel: '金属名称' },
                { name: 'metalPurity', fieldLabel: '金属纯度' },
                { name: 'weight', fieldLabel: '货品重量' },
                { name: 'certificateWeight', fieldLabel: '证书重量' },
                { name: 'nameOfTag', fieldLabel: '标签名称' },
                { name: 'pricingMethod', fieldLabel: '货品计价方式' },
                { name: 'certificateCode', fieldLabel: '证书号' },
                { name: 'goldWeight', fieldLabel: '金重量' },
                { name: 'goldPrice', fieldLabel: '金单价' },
                { name: 'remark', fieldLabel: '货品备注' },
                { name: 'rabbet', fieldLabel: '镶口' },
                { name: 'gemName', fieldLabel: '宝石名称' },
                { name: 'gemCode', fieldLabel: '宝石石号' },
                { name: 'gemColor', fieldLabel: '宝石颜色' },
                { name: 'gemPurity', fieldLabel: '宝石净度' },
                { name: 'gemCut', fieldLabel: '宝石切工' },
                { name: 'gemCount', fieldLabel: '宝石数量' },
                { name: 'gemWeight', fieldLabel: '宝石重量' },
                { name: 'gemRemark', fieldLabel: '宝石备注' },
                { name: 'sideStoneName', fieldLabel: '辅石名称' },
                { name: 'sideStoneCount', fieldLabel: '辅石数量' },
                { name: 'sideStoneWeight', fieldLabel: '辅石重量' },
                { name: 'sideStoneCode', fieldLabel: '辅石石号' },
                { name: 'procurementSettlementPrice', fieldLabel: '总采购成本'},
                { name: 'retailPrice', fieldLabel: '零售价' },
                { name: 'settlementMode', fieldLabel: '结算模式' },
                { name: 'eCommerceNumber', fieldLabel: '电商序列号' }
            ]

        });
        me.callParent(arguments);
    }
});