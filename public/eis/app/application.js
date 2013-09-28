Ext.define('Bling.Application', {
    name: 'Bling',

    extend: 'Ext.app.Application',

    views: [
        "Viewport",
        "Header",
        "Navigation",
        "ContentPanel",
        "shared.CompanyCombo",
        "shared.SourceBizTypeCombo",
        "shared.RedOrBlueCombo"
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
        'wms.ArticleSearchTabWin',
        'wms.DistributionAllocationGrid',
        'wms.DistributionAllocationEditor',
        'wms.OutwardProcessingGrid',
        'wms.OutwardProcessingEditor',
        'wms.PriceAdjustmentGrid',
        'wms.PriceAdjustmentEditor',
				'wms.SellOrderMgmtGrid',
				'wms.PreOrderMgmtGrid',
				'wms.CustomOrderMgmtGrid',
				'wms.FixOrderMgmtGrid',
        'sys.ScheduleGrid'
    ],

    stores: [
        'foundation.Companies',
        'wms.ReceivingOrders',
        'wms.Warehouses',
        'wms.RegionalAllocations',
        'wms.DistributionAllocations',
        'wms.OutwardProcessings',
        'wms.PriceAdjustments',
        'sys.Schedules'
    ]
});
