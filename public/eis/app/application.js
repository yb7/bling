Ext.define('Bling.Application', {
    name: 'Bling',

    extend: 'Ext.app.Application',

    views: [
        "Viewport",
        "Header",
        "Navigation",
        "ContentPanel",
        "shared.CompanyCombo",
        "shared.SourceBizTypeCombo"
    ],

    controllers: [
        'Navigation',
        'ContentPanel',
        'shared.UploadFileWin',
        'foundation.CompanyMgmtGrid',
        'wms.ReceivingOrderMgmtGrid',
        'wms.ReceivingOrderEditor',
        'wms.RegionalAllocations',
        'wms.RegionalAllocationEditor',
        'wms.WarehouseMgmtGrid',
        'wms.ArticleSearchTabWin'
    ],

    stores: [
        'foundation.Companies',
        'wms.ReceivingOrders',
        'wms.Warehouses',
        'wms.RegionalAllocations'
    ]
});
