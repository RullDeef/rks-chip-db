package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import ru.spacecorp.mobdekbkp.entity.ImportDevice;

public interface ImportFrame {

    /**
     * Метод, вызываемый формой типономинала при изменении фокуса на таблицах
     * срабатывает:
     * - если выбрать Изделие ЭКБ;
     * - если выбрать Тип ЭКБ;
     * - если было выбрано Изделие ЭКБ, и фокус сменился на Тип ЭКБ.
     */
    void initFrame(ImportDevice tn);

    /**
     * Метод, вызываемый формой типономинала. Должен очищать все поля и таблицы фрейма
     * и выводить лейбл с надписью, переданной в аргументе.
     */
    void clearFrame(String labelValue);

}
