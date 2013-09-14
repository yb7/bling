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
        'ContentPanel',
        'shared.UploadFileWin',
        'foundation.CompanyMgmtGrid',
        'wms.ReceivingOrderMgmtGrid',
        'wms.ReceivingOrderEditor',
        'wms.RegionalAllocation',
        'wms.RegionalAllocationCreator',
        'wms.WarehouseMgmtGrid'
    ],

    stores: [
        'foundation.Companies',
        'wms.ReceivingOrders',
        'wms.Warehouses'
    ]
});
