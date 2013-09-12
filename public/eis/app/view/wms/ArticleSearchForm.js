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
                { fieldLabel: '货品编码' },
                { fieldLabel: '供应商1' },
                { fieldLabel: '供应商2' },
                { fieldLabel: '供应商3' },
                { fieldLabel: '厂家货号' },
                { fieldLabel: '厂家品名' },
                { fieldLabel: '批号' },
                { fieldLabel: '货物品种' },
                { fieldLabel: '样式' },
                { fieldLabel: '源单类型' },
                { fieldLabel: '源单编号' },
                { fieldLabel: '款号' },
                { fieldLabel: '金属名称' },
                { fieldLabel: '金属纯度' },
                { fieldLabel: '宝石名称' },
                { fieldLabel: '石号' },
                { fieldLabel: '宝石重量' },
                { fieldLabel: '售价范围' },
                { fieldLabel: '结算价范围' },
                { fieldLabel: '货品名称' },
                { fieldLabel: '标签名称' },
                { fieldLabel: '计价方式' },
                { fieldLabel: '证书号' },
                { fieldLabel: '货品备注' },
                { fieldLabel: '开始日期' },
                { fieldLabel: '结束日期' },
                { fieldLabel: '区域' },
                { fieldLabel: '柜台' },
                { fieldLabel: '结算模式' },
                { fieldLabel: '电商序列号' }

            ]

        });
        me.callParent(arguments);
    }
});