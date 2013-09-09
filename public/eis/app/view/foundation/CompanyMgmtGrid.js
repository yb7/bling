Ext.define("Bling.view.foundation.CompanyMgmtGrid", {
    extend: 'Ext.grid.Panel',
    title: '公司管理',
    xtype: 'company-mgmt-grid',
    requires: [
        'Ext.toolbar.TextItem',
        'Ext.form.field.Checkbox',
        'Ext.form.field.Text',
        'Ext.ux.statusbar.StatusBar'
//        'Bling.store.foundation.Companies'
    ],


    store: 'foundation.Companies',



    // Component initialization override: adds the top and bottom toolbars and setup headers renderer.
    initComponent: function() {
        var me = this;
//        var store = Ext.create('Bling.store.foundation.Companies');
        var config = {
//            store: store,

            tbar: ['Search',{
                xtype: 'textfield',
                name: 'searchField',
                hideLabel: true,
                width: 200
            }, {
                xtype: 'button',
                text: '&lt;',
                itemId: 'previousSearch',
                tooltip: '上一条',
                scope: me
            },{
                xtype: 'button',
                text: '&gt;',
                itemId: 'nextSearch',
                tooltip: '下一条',
                scope: me
            }, '-', {
                xtype: 'checkbox',
                hideLabel: true,
                margin: '0 0 0 4px',
                itemId: 'regExpToggle',
                scope: me
            }, '正则表达式', {
                xtype: 'checkbox',
                hideLabel: true,
                margin: '0 0 0 4px',
                itemId: 'caseSensitiveToggle',
                scope: me
            }, '大小写敏感', '-', {
                xtype: 'button',
                itemId: 'createBtn',
                text: '新建'
            }, {
                xtype: 'button',
                itemId: 'deleteBtn',
                disabled: true,
                text: '删除'
            }],

            columns: [
                {text: 'ID', dataIndex: 'id', hidden: true},
                {text: '简码', dataIndex: 'shortCode'},
                {text: '名称', dataIndex: 'name'}
            ],

            bbar: {
                xtype:'statusbar',
                defaultText: 'Nothing Found',
                name: 'searchStatusBar'
            }
        };

        Ext.apply(me, config);
        me.callParent(arguments);
    }
});