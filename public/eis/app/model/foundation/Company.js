Ext.define('Bling.model.foundation.Company', {
    extend: 'Ext.data.Model',
    requires: ['Ext.data.proxy.Rest'],
    fields: [
        {name: 'id', type: 'int', convert: null},
        {name: 'version', type: 'int'},
        {name: 'shortCode'},
        {name: 'name'}
    ],
    proxy: {
        type: 'rest',
        url : '/eis/foundation/companies',
        reader: {
            type: 'json',
            root: 'data'
        },
        writer: {
            type: 'json'
        }
    }

});