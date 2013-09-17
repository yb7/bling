Ext.define('Bling.model.sys.Schedule', {
    extend: 'Ext.data.Model',
    requires: ['Ext.data.proxy.Rest'],
    fields: [
        {name: 'triggerKey'},
        {name: 'description'},
        {name: 'previousFireTime'},
        {name: 'nextFireTime'},
        {name: 'state'},
        {name: 'jobKey'}
    ],
    idProperty: 'triggerKey',
    proxy: {
        type: 'rest',
        url : '/eis/sys/job/triggers',
        reader: {
            type: 'json',
            root: 'data'
        },
        writer: {
            type: 'json'
        }
    }

});