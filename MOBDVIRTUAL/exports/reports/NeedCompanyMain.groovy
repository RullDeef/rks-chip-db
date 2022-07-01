def result = []

def currentYear = params['yr']
def currentCompany = params['manufacturer']
int enumerator = 0
def total = 0
def QUERY_STRING = 'select cn from mobdekbkp$CompanyNeed cn join cn.typonominal.type.manufacturers man where man.name.id = ?1'
def QUERY_YEAR = ' and extract(year from cn.wantedDeliverDate) = ?2'
def QUERY_ORDER = ' order by cn.typonominal.name'

transactional { em ->
    def needs
    if (currentYear == null) {
        needs = em.createQuery(QUERY_STRING + QUERY_ORDER)
        needs.setParameter(1, currentCompany.id)
    } else {
        currentYear = Integer.parseInt(params['yr'])
        needs = em.createQuery(QUERY_STRING + QUERY_YEAR + QUERY_ORDER)
        needs.setParameter(1, currentCompany.id)
        needs.setParameter(2, currentYear)
    }

    def prevNeed;
    def prevQual;
    needs.resultList.each { e ->
        enumerator++
        def rus = e.isRussian()
        def qlevel = e.getQualityCaption()
        def mans = e.typonominal.type.manufacturers

        def companiesList = ""
        mans.each { c ->
            companiesList += (c.name.smartName + "; ")
        }

        if (prevNeed == null) {
            total = e.amount
        } else if (e.typonominal.name == prevNeed.typonominal.name) {
            total += e.amount
        } else {
            result.add([
                    'enum'       : 'Итого: ',
                    'designation': prevNeed.typonominal.name,
                    'qlevel'     : prevQual,
                    'company'    : ' ',
                    'amount'     : total,
            ])
            total = e.amount
        }
        prevNeed = e
        prevQual = qlevel

        result.add([
                'enum'       : enumerator,
                'designation': e.typonominal.name,
                'qlevel'     : qlevel,
                'company'    : e.company.smartName,
                'amount'     : e.amount,
        ])
    }
    if (prevNeed != null) {
        result.add([
                'enum'       : 'Итого',
                'designation': prevNeed.typonominal.name,
                'qlevel'     : prevQual,
                'company'    : ' ',
                'amount'     : total,
        ])
    }

}
return result
