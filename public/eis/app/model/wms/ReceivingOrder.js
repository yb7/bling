Ext.define('Bling.model.wms.ReceivingOrder', {
    extend: 'Ext.data.Model',
    requires: ['Ext.data.proxy.Rest'],
    fields: [
        {name: 'id', type: 'int', useNull: true},
        {name: 'version', type: 'int'},
        {name: 'bizCode'},
        {name: 'receivingDate', type:'date'},
        {name: 'warehouseId', type: 'int'},
        {name: 'warehouseName', type: 'string'}
    ],
    proxy: {
        type: 'rest',
        url : '/eis/wms/receiving-orders',
        reader: {
            type: 'json',
            root: 'data'
        },
        writer: {
            type: 'json'
        }
    }
});