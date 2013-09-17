Ext.define('Bling.model.wms.PriceAdjustment', {
    extend: 'Ext.data.Model',
    requires: ['Ext.data.proxy.Rest'],
    fields: [
        {name: 'id', type: 'int', useNull: true},
        {name: 'version', type: 'int'},
        {name: 'bizCode'},
        {name : 'remark', type: 'string'},
        {name : 'effectiveDate', type: 'date'},
        {name : 'retailPriceDirectly', type: 'float'},

        {name : 'incrementBaseOnCost', type: 'float'},
        {name : 'coefficientBaseOnCost', type: 'float'},

        {name : 'incrementBaseOnRetailPrice', type: 'float'},
        {name : 'coefficientBaseOnRetailPrice', type: 'float'},

        {name : 'excludeUnits', type: 'string'},
        {name : 'excludeUnits0', type: 'boolean'},
        {name : 'excludeUnits1', type: 'boolean'},
        {name : 'excludeUnits2', type: 'boolean'},
        {name : 'excludeUnits3', type: 'boolean'},
        {name : 'excludeUnits4', type: 'boolean'},
        {name : 'excludeUnits5', type: 'boolean'},
        {name : 'excludeUnits6', type: 'boolean'},
        {name : 'excludeUnits7', type: 'boolean'},
        {name : 'excludeUnits8', type: 'boolean'},
        {name : 'excludeUnits9', type: 'boolean'}
    ],
    proxy: {
        type: 'rest',
        url : '/eis/wms/price-adjustments',
        reader: {
            type: 'json',
            root: 'data'
        },
        writer: {
            type: 'json'
        }
    }
});