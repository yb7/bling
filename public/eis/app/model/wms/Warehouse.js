Ext.define('Bling.model.wms.Warehouse', {
    extend: 'Ext.data.Model',
    requires: ['Ext.data.proxy.Rest'],
    fields: [
        {name: 'id', type: 'int', useNull: true},
        {name: 'version', type: 'int'},
        {name: 'shortCode'},
        {name: 'name'}
    ],
    validations: [{
        type: 'length',
        field: 'shortCode',
        min: 1
    }, {
        type: 'length',
        field: 'name',
        min: 1
    }],
    proxy: {
        type: 'rest',
        url : '/eis/wms/warehouses',
        reader: {
            type: 'json',
            root: 'data'
        },
        writer: {
            type: 'json'
        }
    }

});