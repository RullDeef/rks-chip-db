import com.haulmont.cuba.core.global.Messages
import com.haulmont.cuba.core.global.AppBeans

// Переработать под актуальную статусную модель и процесс согласования (уточняется у Вити)
def QUERY_ALL = 'select ap from mobdekbkp$ApplicationNewTyponominalDev ap where ap.status <> @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalDevStatus.created)'
def QUERY_DEC = 'select ap from mobdekbkp$ApplicationNewTyponominalDev ap where ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalDevStatus.declined)'
def QUERY_ACC = 'select ap from mobdekbkp$ApplicationNewTyponominalDev ap where ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalDevStatus.accepted)'
def QUERY_GNIO = 'select ap from mobdekbkp$ApplicationNewTyponominalDev ap where ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalDevStatus.inGnio) or ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalDevStatus.onApproval)'
def QUERY_APPR = 'select ap from mobdekbkp$ApplicationNewTyponominalDev ap where ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalDevStatus.inGnio) or ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalDevStatus.onApproval) or ap.status = @enum(ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalDevStatus.onEdit)'

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
    query.setParameter(1, theme0)
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
    'applicdev': 'Заявка на разработку нового элемента ЭКБ'
    'adppc': onappr.size(), 'adppr': onappr.size() / all.size() * 100,
    'adrpc': accepted.size(), 'adrpr': accepted.size() / all.size() * 100,
    'adgpc': ingnio.size(), 'adgpr': ingnio.size() / all.size() * 100,
    'admpc': '-', 'admpr': '-',
    'adnpc': '-', 'adnpr': '-',
    'addpc': declined.size(), 'addpr': declined.size() / all.size() * 100,
    'adtpc': all.size(),
    // Запрос и столбец отчёта имени Виктора Бондяшова.
    'adspc': support.size(), 'adspr': supportall.toString(),

]]
