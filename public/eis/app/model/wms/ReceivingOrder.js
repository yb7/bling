Ext.define('Bling.model.wms.ReceivingOrder', {
    extend: 'Ext.data.Model',
    requires: ['Ext.data.proxy.Rest'],
    fields: [
        {name: 'id', type: 'int', convert: null},
        {name: 'version', type: 'int'},
        {name: 'bizCode'},
        {name: 'receivingTime', type:'date'}
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