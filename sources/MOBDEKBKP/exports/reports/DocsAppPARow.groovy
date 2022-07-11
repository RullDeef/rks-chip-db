import com.haulmont.cuba.core.global.Messages
import com.haulmont.cuba.core.global.AppBeans

def QUERY_ALL = 'select ap from mobdekbkp$ApplicationNewTyponominalAdd ap where ap.status <> @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus.created)'
def QUERY_DEC = 'select ap from mobdekbkp$ApplicationNewTyponominalAdd ap where ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus.declined)'
def QUERY_ACC = 'select ap from mobdekbkp$ApplicationNewTyponominalAdd ap where ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus.accepted)'
def QUERY_GNIO = 'select ap from mobdekbkp$ApplicationNewTyponominalAdd ap where ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus.inGnio) or ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus.onApproval)'
def QUERY_APPR = 'select ap from mobdekbkp$ApplicationNewTyponominalAdd ap where ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus.inGnio) or ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus.onApproval) or ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus.onEdit)'

// Запросы и константы имени Виктора Бондяшова: Выяснение количества обращений в техподдержку
def QUERY_SUPP = 'select nm from notificationsusers$Message nm where nm.subject = ?1'
def QUERY_SALL = 'select nm from notificationsusers$Message nm where nm.subject = ?1 or nm.subject = ?2 or nm.subject = ?3 or nm.subject = ?4'

Messages messages = AppBeans.get(Messages)
// желательно переисать эти магические строки в вызов локализованного сообщения
// messages.getMessage('package-name', 'property-string')
def theme = 'Согласование заявки на добавление нового элемента ЭКБ в МОБД ЭКБ КП'
def theme0 = 'Согласование заявки на разработку нового элемента ЭКБ'
def theme1 = 'Согласование перечня проектного для изделий РКТ'
def theme2 = 'Согласование дополнений к перечню проектному'

def all
def accepted
def declined
def ingnio
def onappr
def support
float supportall
transactional { em ->
    def query = em.createQuery(QUERY_ALL)
    all = query.resultList
    query = em.createQuery(QUERY_DEC)
    declined = query.resultList
    query = em.createQuery(QUERY_ACC)
    accepted = query.resultList
    query = em.createQuery(QUERY_GNIO)
    ingnio = query.resultList
    query = em.createQuery(QUERY_APPR)
    onappr = query.resultList

    // Запрос отчёта имени Виктора Бондяшова.
    query = em.createQuery(QUERY_SUPP)
    query.setParameter(1, theme)
    support = query.resultList
    query = em.createQuery(QUERY_SALL)
    query.setParameter(1, theme)
    query.setParameter(2, theme0)
    query.setParameter(3, theme1)
    query.setParameter(4, theme2)
    supportall = query.resultList.size()
    supportall = (support.size() / ((supportall == 0) ? 1 : supportall)) * 100f
    
}
return [[
    'applicadd': 'Заявка на добавление нового элемента ЭКБ в МОБД ЭКБ КП',
    'aappc': onappr.size(), 'aappr': onappr.size() / all.size() * 100,
    'aarpc': accepted.size(), 'aarpr': accepted.size() / all.size() * 100,
    'aagpc': ingnio.size(), 'aagpr': ingnio.size() / all.size() * 100,
    'aampc': '-', 'aampr': '-',
    'aanpc': '-', 'aanpr': '-',
    'aadpc': declined.size(), 'aadpr': declined.size() / all.size() * 100,
    'aatpc': all.size(),
    // Запрос и столбец отчёта имени Виктора Бондяшова.
    'aaspc': support.size(), 'aaspr': supportall.toString(),

]]
