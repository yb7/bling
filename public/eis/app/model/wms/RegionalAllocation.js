Ext.define('Bling.model.wms.RegionalAllocation', {
    extend: 'Ext.data.Model',
    requires: ['Ext.data.proxy.Rest'],
    fields: [
        {name: 'id', type: 'int', useNull: true},
        {name: 'version', type: 'int'},
        {name: 'bizCode'},
        {name: 'allocationDate', type:'date'},
        {name: 'receiveWarehouseId', type: 'int'},
        {name: 'receiveWarehouseName', type: 'string'},
        {name: 'remark', type: 'string'}
    ],
    proxy: {
        type: 'rest',
        url : '/eis/wms/regional-allocations',
        reader: {
            type: 'json',
            root: 'data'
        },
        writer: {
            type: 'json'
        }
    }
});