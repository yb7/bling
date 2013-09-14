Ext.define("Bling.view.wms.TallyArticleGrid", {
    extend: 'Ext.grid.Panel',
    xtype: 'tally-article-grid',
    requires: [
        'Ext.layout.container.Column',
        'Ext.grid.column.RowNumberer',
        'Ext.grid.column.Number',
        'Ext.grid.column.Date',
        'Ext.grid.column.Boolean',
        'Ext.grid.column.Template'
    ],

    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            autoScroll: true,
            scroll: true,
            columns: [
                { xtype: 'rownumberer', text: '#' },
                { dataIndex: 'id', text: 'ID', hidden: true },
                { dataIndex: 'code', text: '商品编码' },
                { dataIndex: 'vendor1Name', text: '供应商1' },
                { dataIndex: 'vendor2Name', text: '供应商2' },
                { dataIndex: 'vendor3Name', text: '供应商3' },
                { dataIndex: 'vendorArticleName', text: '厂家品名' },
                { dataIndex: 'vendorArticleNumber', text: '厂家货号' },
                { dataIndex: 'name', text: '货品名称' },
                { dataIndex: 'category', text: '货品品种' },
                { dataIndex: 'style', text: '样式' },
                { dataIndex: 'lotNumber', text: '批号' },
                { dataIndex: 'styleNumber', text: '款号' },
                { dataIndex: 'handInch', text: '手寸' },
                { dataIndex: 'metalName', text: '金属名称' },
                { dataIndex: 'metalPurity', text: '金属纯度' },
                { dataIndex: 'weight', text: '货品重量' },
                { dataIndex: 'certificateWeight', text: '证书重量' },
                { dataIndex: 'tagName', text: '标签名称' },
                { dataIndex: 'pricingMethod', text: '货品计价方式' },
                { dataIndex: 'certificateCode', text: '证书号' },
                { dataIndex: 'goldWeight', text: '金重量' },
                { dataIndex: 'goldPrice', text: '金单价' },
                { dataIndex: 'remark', text: '货品备注' },
                { dataIndex: 'rabbet', text: '镶口' },
                { dataIndex: 'gemName', text: '宝石名称' },
                { dataIndex: 'gemCode', text: '宝石石号' },
                { dataIndex: 'gemColor', text: '宝石颜色' },
                { dataIndex: 'gemPurity', text: '宝石净度' },
                { dataIndex: 'gemCut', text: '宝石切工' },
                { dataIndex: 'gemCount', text: '宝石数量' },
                { dataIndex: 'gemWeight', text: '宝石重量' },
                { dataIndex: 'gemRemark', text: '宝石备注' },
                { dataIndex: 'sideStoneName', text: '辅石名称' },
                { dataIndex: 'sideStoneCount', text: '辅石数量' },
                { dataIndex: 'sideStoneWeight', text: '辅石重量' },
                { dataIndex: 'sideStoneCode', text: '辅石石号' },
                { dataIndex: 'procurementSettlementPrice1', text: '采购结算价1' },
                { dataIndex: 'procurementSettlementPrice2', text: '采购结算价2' },
                { dataIndex: 'procurementSettlementPrice3', text: '采购结算价3' },
                { text: '总采购成本', xtype:'templatecolumn', tpl:'{procurementSettlementPrice1 + procurementSettlementPrice2 + procurementSettlementPrice3}'},
                { dataIndex: 'retailPrice', text: '零售价' },
                { dataIndex: 'settlementMode', text: '结算模式' },
                { dataIndex: 'eCommerceNumber', text: '电商序列号' }
            ]
        });

        me.callParent(arguments);
    }
});