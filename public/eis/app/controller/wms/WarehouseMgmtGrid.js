Ext.define('Bling.controller.wms.WarehouseMgmtGrid', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.model.wms.Warehouse', 'Ext.grid.plugin.RowEditing'],
    models: ['wms.Warehouse'],
    refs: [
        {
            selector: 'warehouse-mgmt-grid',
            ref: 'warehouseMgmtGrid'
        }
    ],



    init: function() {
        var me = this;
        this.control({
            'warehouse-mgmt-grid': {
                afterrender: function(cmp){
                    cmp.getStore().load();
                },
                selectionchange: function(selModel, selections){
                    this.getWarehouseMgmtGrid().down('#deleteBtn').setDisabled(selections.length === 0);
                }
            },

            'warehouse-mgmt-grid #createBtn': {
                click: function(){
                    // empty record
                    var model = this.getModel('wms.Warehouse').create();
                    this.getWarehouseMgmtGrid().getStore().insert(0, {});
                    this.getWarehouseMgmtGrid().findPlugin('rowediting').startEdit(0, 0);
                }
            },
            'warehouse-mgmt-grid #deleteBtn': {
                click: function() {
                    var selected = this.getWarehouseMgmtGrid().getSelectionModel().getSelection()[0];
                    if (selected) {
                        this.getWarehouseMgmtGrid().getStore().remove(selected);
                    }
                }
            }
        })
    }
});
