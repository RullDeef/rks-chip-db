def result = []

def currentYear = params['yr']
int enumerator = 0
def total = 0
def QUERY_STRING = 'select cn from mobdekbkp$CompanyNeed cn'
def QUERY_YEAR = ' where (extract(year from cn.wantedDeliverDate) = ?1)'
def QUERY_ORDER = ' order by cn.typonominal.name'

transactional { em->
    def needs;
    if (currentYear == null) {
        needs = em.createQuery(QUERY_STRING + QUERY_ORDER)
    } else {
        currentYear = Integer.parseInt(params['yr'])
        needs = em.createQuery(QUERY_STRING + QUERY_YEAR + QUERY_ORDER)
        needs.setParameter(1, currentYear)
    }

	def prevNeed;
	def prevQual;
    needs.resultList.each { e->
		enumerator++
		def rus = e.isRussian()
		def qlevel = e.getQualityCaption()
		def mans = e.typonominal.type.manufacturers

		def companiesList = ""
		mans.each { c ->
			companiesList += c.name.smartName
			if (!mans.last()) companiesList += "; "
		}

		if (prevNeed == null) {
			total = e.amount
		} else if (e.typonominal.name == prevNeed.typonominal.name) {
			total += e.amount
		} else {
			result.add([
				'enum': 'Итого: ',
				'designation': prevNeed.typonominal.name,
				'qlevel': prevQual,
				'amount': total,
				'companies': prevNeed.company.smartName,	
				'developers': ' '
				])
			total = e.amount
		}
		prevNeed = e
		prevQual = qlevel

        result.add([
				'enum': enumerator,
				'designation': e.typonominal.name,
				'qlevel': qlevel,
				'amount': e.amount,
				'companies': e.company.smartName,	
				'developers': companiesList
			])
    }
	if (prevNeed != null) {
		result.add([
			'enum': 'Итого',
			'designation': prevNeed.typonominal.name,
			'qlevel': prevQual,
			'amount': total,
			'companies': ' ',
			'developers': ' '
			])
		}

}
return result
