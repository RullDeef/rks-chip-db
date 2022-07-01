//band mainlines
def from = params['from']
def to = params['to']

def MAIN_QUERY =
        'select dpl from mobdekbkp$DeviceListProject dpl join dpl.device dev where dpl.status = @enum(ru.spacecorp.mobdekbkp.entity.DeviceListProjectStatus.readyForUse) and dpl.approvalDate >= ?1 and dpl.approvalDate <= ?2'

int enumerator = 0
def result = []
transactional { em ->
    def query = em.createQuery(MAIN_QUERY)
    query.setParameter(1, from)
    query.setParameter(2, to)
    query.resultList.each { dpl ->
        enumerator++
        int amountImp = 0
        int amountNat = 0

        dpl.entries.each { e ->
            if (e.status == ru.spacecorp.mobdekbkp.entity.DeviceListEntryStatus.approved) {
                if (e.typonominal.isRussian()) {
                    amountNat++
                } else {
                    amountImp++
                }
            }
        }

        int amntAll = amountNat + amountImp
        float prcNat = (amntAll == 0) ? 0 : amountNat / (float) (amntAll) * 100f
        float prcImp = (amntAll == 0) ? 0 : amountImp / (float) (amntAll) * 100f


        result.add([
                'enum'       : enumerator,
                'dev'        : dpl.device.designation,
                'importpcs'  : amountImp,
                'importprc'  : prcImp,
                'nativepcs'  : amountNat,
                'nativeprc'  : prcNat,
                'total'      : amntAll,
                'sublevel'   : '',
                'dependlevel': ''
        ])

    }


}

return result
