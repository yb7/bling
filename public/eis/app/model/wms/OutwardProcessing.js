Ext.define('Bling.model.wms.OutwardProcessing', {
    extend: 'Ext.data.Model',
    requires: ['Ext.data.proxy.Rest'],
    fields: [
        {name: 'id', type: 'int', useNull: true},
        {name: 'version', type: 'int'},
        {name: 'bizCode'},
        {name: 'redOrBlue', type:'string'},
        {name: 'outwardDate', type:'date'},
        {name: 'expectedCompletionDate', type:'date'},
        {name: 'receiveWarehouseId', type: 'int'},
        {name: 'receiveWarehouseName', type: 'string'},
        {name: 'remark', type: 'string'}
    ],
    proxy: {
        type: 'rest',
        url : '/eis/wms/outward-processings',
        reader: {
            type: 'json',
            root: 'data'
        },
        writer: {
            type: 'json'
        }
    }
});