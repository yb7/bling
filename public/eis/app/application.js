Ext.define('Bling.Application', {
    name: 'Bling',

    extend: 'Ext.app.Application',

    views: [
        "Viewport",
        "Header",
        "Navigation",
        "ContentPanel"
    ],

    controllers: [
        'Navigation',
        'foundation.CompanyMgmtGrid',
        'wms.EnteringWarehouseMgmtGrid',
        'wms.RegionalAllocation',
        'wms.RegionalAllocationCreator'
    ],

    stores: [
        'foundation.Companies'
    ]
});
