Ext.define("Bling.view.wms.WarehouseMgmtGrid", {
    extend: 'Ext.grid.Panel',
    title: '仓库管理',
    xtype: 'warehouse-mgmt-grid',
    requires: [
        'Ext.grid.plugin.RowEditing'
    ],


    initComponent: function() {
        var me = this;
        var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            listeners: {
                cancelEdit: function(rowEditing, context) {
                    // Canceling editing of a locally added, unsaved record: remove it
                    if (context.record.phantom) {
                        me.store.remove(context.record);
                    }
                }
            }
        });
        var config = {
            store: 'wms.Warehouses',
            dockedItems: [{
                xtype:'toolbar',
                dock: 'top',
                ui: 'footer',
                items: ['->', {
                    xtype: 'button',
                    itemId: 'createBtn',
                    text: '新建'
                }, {
                    xtype: 'button',
                    itemId: 'deleteBtn',
                    disabled: true,
                    text: '删除'
                }]
            }],
            plugins: [rowEditing],

            columns: [
                {
                    text: 'ID', dataIndex: 'id', hidden: true
                },
                {
                    text: '简码', dataIndex: 'shortCode',
                    width: 150,
                    editor: {
                        allowBlank: false
                    }
                },
                {
                    text: '名称', dataIndex: 'name',
                    flex: 1,
                    editor: {
                        allowBlank: false
                    }
                }
            ]
        };

        Ext.apply(me, config);
        me.callParent(arguments);
    }
});