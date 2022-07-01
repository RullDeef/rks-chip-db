def result = []
def currentElement = params['element']
def currentName = currentElement.name.toString()

static def setPartsNames(Map mapOfParts, Object e) {
    def partDesignation = e.devicePartListComplect.devicePart.designation

    if (mapOfParts.get(partDesignation) == null) {
        def partsList = []
        partsList.add(e)
        mapOfParts.put(partDesignation, partsList)
    } else {
        ((List) mapOfParts.get(partDesignation)).add(e)
    }
}

// find all complect entries whith given TN
transactional { em ->
    def query = em.createQuery('select c from mobdekbkp$DevicePartListComplectEntry c where c.typonominal.name = ?1')
    query.setParameter(1, currentName)
    TreeMap mainMap = [:]                   // [year:[part1, part2]] year and all parts in this year
    query.resultList.each { e ->
        if (e.datePlanned != null) {                            // no year - no need
            def neededYear = e.datePlanned[Calendar.YEAR]
            if (mainMap.get(neededYear) == null) {              // first time got year
                TreeMap partsMap = [:]                             // create parts map
                setPartsNames(partsMap, e)
                mainMap.put(neededYear, partsMap)
            } else {
                setPartsNames((Map) mainMap.get(neededYear), e)
            }
        }
    }
    int overall = 0;
    mainMap.each { y ->         // entries year : partsMap
        def enumerator = 0
        int yearNeed = 0
        int yearInst = 0
        int yearDiff = 0
        y.value.each { p ->     // entries partName: partsList
            int need = 0
            int installed = 0
            p.value.each { i ->
                need = need + ((i.amountTotal != null) ? i.amountTotal : 0)
                installed = installed + ((i.amountDelivered != null) ? i.amountDelivered : 0)
            }
            def delta = need - installed
            enumerator++
            result.add([(overall.toString()): [
                    'enum'                : enumerator,
                    'planYear'            : (enumerator == 1) ? y.key.toString() : '',
                    'deviceDesignation'   : p.key,
                    'deviceKitsCount'     : p.value.size(),
                    'prjEntriesAmount'    : need,
                    'complEntriesAmount'  : installed,
                    'deltaProjectComplect': delta
            ]])
            overall++
            yearInst = yearInst + installed
            yearNeed = yearNeed + need
            yearDiff = yearNeed - yearInst
        }
        result.add([(overall.toString()): [
                'planYear'            : 'Итого:',
                'prjEntriesAmount'    : yearNeed,
                'complEntriesAmount'  : yearInst,
                'deltaProjectComplect': yearDiff
        ]])
        overall++
        result.add(['-': [:]])
        overall++
    }
}
return result
