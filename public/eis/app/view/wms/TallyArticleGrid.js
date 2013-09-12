Ext.define("Bling.view.wms.TallyArticleGrid", {
    extend: 'Ext.grid.Panel',
    xtype: 'tally-article-grid',
    requires: [
        'Ext.layout.container.Column',
        'Ext.grid.column.RowNumberer',
        'Ext.grid.column.Number',
        'Ext.grid.column.Date',
        'Ext.grid.column.Boolean'
    ],

    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            autoScroll: true,
            scroll: true,
//            layout : 'fit',
            columns: [
                { xtype: 'rownumberer', text: '#' },
                { xtype: 'gridcolumn', dataIndex: 'string', text: 'ID', hidden: true },
                { xtype: 'numbercolumn', dataIndex: 'number', text: '商品编码' },
                { xtype: 'datecolumn', dataIndex: 'date', text: '供应商1' },
                { xtype: 'booleancolumn', dataIndex: 'bool', text: '供应商2' },
                { text: '供应商3' },
                { text: '厂家品名' },
                { text: '厂家货号' },
                { text: '货品名称' },
                { text: '货品品种' },
                { text: '样式' },
                { text: '批号' },
                { text: '款号' },
                { text: '手寸' },
                { text: '金属名称' },
                { text: '金属纯度' },
                { text: '货品重量' },
                { text: '证书重量' },
                { text: '标签名称' },
                { text: '货品计价方式' },
                { text: '证书号' },
                { text: '货品备注' },
                { text: '金重量' },
                { text: '金单价' },
                { text: '镶口' },
                { text: '宝石名称' },
                { text: '宝石石号' },
                { text: '宝石颜色' },
                { text: '宝石净度' },
                { text: '宝石切工' },
                { text: '宝石数量' },
                { text: '宝石重量' },
                { text: '宝石备注' },
                { text: '辅石名称' },
                { text: '辅石数量' },
                { text: '辅石重量' },
                { text: '辅石石号' },
                { text: '零售价' },
                { text: '采购结算价1' },
                { text: '采购结算价2' },
                { text: '采购结算价3' },
                { text: '总采购成本' },
                { text: '结算模式' }
            ]
        });

        me.callParent(arguments);
    }
});