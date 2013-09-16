Ext.define('Bling.model.wms.DistributionAllocation', {
    extend: 'Ext.data.Model',
    requires: ['Ext.data.proxy.Rest'],
    fields: [
        {name: 'id', type: 'int', useNull: true},
        {name: 'version', type: 'int'},
        {name: 'bizCode'},
        {name: 'distributionDate', type:'date'},
        {name: 'receiveWarehouseId', type: 'int'},
        {name: 'receiveWarehouseName', type: 'string'},
        {name: 'remark', type: 'string'}
    ],
    proxy: {
        type: 'rest',
        url : '/eis/wms/distribution-allocations',
        reader: {
            type: 'json',
            root: 'data'
        },
        writer: {
            type: 'json'
        }
    }
});