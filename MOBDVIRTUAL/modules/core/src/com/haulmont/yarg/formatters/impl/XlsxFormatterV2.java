//package com.haulmont.yarg.formatters.impl;
//
//import com.google.common.collect.LinkedHashMultimap;
//import com.google.common.collect.Multimap;
//import com.haulmont.yarg.formatters.factory.FormatterFactoryInput;
//import com.haulmont.yarg.formatters.impl.pdf.HtmlToPdfConverter;
//import com.haulmont.yarg.formatters.impl.xlsx.CellReference;
//import com.haulmont.yarg.formatters.impl.xlsx.Document;
//import com.haulmont.yarg.formatters.impl.xlsx.Range;
//import com.haulmont.yarg.structure.BandData;
//import com.haulmont.yarg.structure.BandOrientation;
//import com.haulmont.yarg.structure.ReportOutputType;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang3.ObjectUtils;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.docx4j.XmlUtils;
//import org.docx4j.dml.chart.*;
//import org.docx4j.dml.spreadsheetdrawing.CTTwoCellAnchor;
//import org.docx4j.model.chart.impl.xchart.*;
//import org.docx4j.openpackaging.exceptions.Docx4JException;
//import org.docx4j.openpackaging.io.SaveToZipFile;
//import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
//import org.docx4j.openpackaging.parts.PartName;
//import org.docx4j.openpackaging.parts.Parts;
//import org.docx4j.openpackaging.parts.SpreadsheetML.SharedStrings;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.xlsx4j.jaxb.Context;
//import org.xlsx4j.sml.*;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicLong;
//import java.util.stream.Collectors;
//
///**
// * @author golovin
// */
//public class XlsxFormatterV2 extends AbstractFormatter {
//
//    static final Double DEF_COL_WIDTH = 9.10526315789474;
//
//    protected static Logger logger = LoggerFactory.getLogger(XlsxFormatterV2.class);
//    static final Comparator<Cell> CELL_COMPARATOR = (c1, c2) -> {
//        int dc = Integer.compare(c1.getR().length(), c2.getR().length());
//        if (dc == 0) {
//            dc = c1.getR().compareTo(c2.getR());
//        }
//        return dc;
//    };
//
//    protected DomContext context;
//    protected HtmlToPdfConverter htmlToPdfConverter;
//
//    public XlsxFormatterV2(FormatterFactoryInput formatterFactoryInput) {
//        super(formatterFactoryInput);
//        supportedOutputTypes.add(ReportOutputType.xlsx);
//    }
//
//    public void setPdfConverter(HtmlToPdfConverter htmlToPdfConverter) {
//        this.htmlToPdfConverter = htmlToPdfConverter;
//    }
//
//    @Override
//    public void renderDocument() {
//        init();
//        DomNode node = DomNode.make(context, rootBand);
//        context.normalizeNodes();
//
//        recursiveProcess(node);
//        updateMergeRegions();
//        processColumnWidths();
//        context.updateDimensions();
//        updateCharts();
//////TODO: updateFormulas();
//        updateConditionalFormatting();
//        saveAndClose();
//    }
//
//    protected void init() {
//        try {
//            context = new DomContext(Document.create((SpreadsheetMLPackage)
//                    SpreadsheetMLPackage.load(reportTemplate.getDocumentContent())));
//        } catch (Exception e) {
//            throw wrapWithReportingException(String.format("An error occurred while loading template [%s]",
//                    reportTemplate.getDocumentName()), e);
//        }
//    }
//
//    protected boolean hasMergedCells(DomNode dataNode) {
//        if (dataNode == null) return false;
//        DefNameHolder childFirstHolder = dataNode.getChilDefName();
//        if (dataNode.getDefName() != null && childFirstHolder != null) {
//            if (dataNode.horizontalMergeCheck()) {
//                //horizontal merge
//                if (dataNode.getDefName().getRange().getFirstRow() < childFirstHolder.getRange().getFirstRow()) {
//                    dataNode.setMergeSize(dataNode.getDefName().getWidth());
//                }
//            } else if (dataNode.verticalMergeCheck()) {
//                //vertical merge
//                if (dataNode.getDefName().getRange().getLastColumn() < childFirstHolder.getRange().getFirstColumn()) {
//                    dataNode.setMergeSize(dataNode.getDefName().getHeight());
//                }
//            }
//        }
//        return dataNode.getMergeSize() > 0;
//    }
//
//    protected void recursiveProcess(DomNode dataNode) {
//        process(dataNode);
//        dataNode.getChildes().values().forEach(this::recursiveProcess);
//        dataNode.getChildes().values().forEach(node -> {
//            dataNode.addRange(node.getRange());
//        });
//        if (hasMergedCells(dataNode)) {
//            int count = dataNode.getMergeSize();
//            while (count-- > 0) {
//                process(DomNode.emptyNode(dataNode));
//            }
//            DefNameHolder holder = dataNode.getDefName();
//            dataNode.getChildes().values().forEach(node -> {
//                holder.getMergeBands().put(dataNode.getRange(), node.getRange());
//            });
//        }
//    }
//
//    protected void process(DomNode dataNode) {
//        if (dataNode.getDefName() == null) return;
//        Range templateRange = dataNode.getDefName().getRange();
//        boolean verticalGrow = BandOrientation.HORIZONTAL.equals(dataNode.getBandData().getOrientation());
//        Integer[] pointer = new Integer[]{templateRange.getFirstRow() + dataNode.verticalOffset() - 1
//                , dataNode.horizontalOffset()};
//        if (verticalGrow) {
//            pointer[0] = pointer[0] + dataNode.getVStep();
//        } else {
//            pointer[1] = pointer[1] + dataNode.getHStep();
//        }
//        List<CellReference> resultCells = copyCells(dataNode, pointer);
//        if (CollectionUtils.isNotEmpty(resultCells)) {
//            resultCells.sort((c1, c2) -> {
//                int dx = Integer.compare(c1.getColumn(), c2.getColumn());
//                if (dx == 0) {
//                    dx = Integer.compare(c1.getRow(), c2.getRow());
//                }
//                return dx;
//            });
//            CellReference first = resultCells.get(0);
//            CellReference last = resultCells.get(resultCells.size() - 1);
//            Range resultRange = new Range(
//                    first.getSheet(),
//                    first.getColumn(),
//                    first.getRow(),
//                    last.getColumn(),
//                    last.getRow()
//            );
//            dataNode.getDefName().updateResultRange(resultRange);
//            dataNode.setRange(resultRange);
//            resultCells.forEach(ref -> {
//                SheetColumn sheetColumn = new SheetColumn(ref.getSheet(), ref.getColumn());
//                Map<Integer, ColumnModelHolder> modelMap = context.getColumnModel(sheetColumn);
//                Col tpl = dataNode.getDefName().generateColumn(resultRange.getFirstColumn(), ref.getColumn());
//                ColumnModelHolder model = modelMap.getOrDefault(tpl.getWidth().intValue(),
//                        new ColumnModelHolder(tpl));
//                model.incrementUsage();
//                modelMap.putIfAbsent(tpl.getWidth().intValue(), model);
//            });
//        }
//    }
//
//    List<CellReference> copyCells(DomNode node, Integer[] startPoint) {
//        List<CellReference> resultCells = new ArrayList<>();
//        Range range = node.getDefName().getRange();
//        for (int i = 0, delta = range.getLastRow() - range.getFirstRow(); i <= delta; i++) {
//            Range oneRowRange = new Range(range.getSheet(),
//                    range.getFirstColumn(), range.getFirstRow() + i,
//                    range.getLastColumn(), range.getFirstRow() + i);
//            Map<CellReference, Cell> cellsForOneRowRange = context.getTemplate().getCellsByRange(oneRowRange);
//            List<Cell> templateCells = new ArrayList<>(cellsForOneRowRange.values());
//            Row templateRow = getRow(node.getDefName().getTemplateSheet(), oneRowRange.getFirstRow() - 1);
//            Row resultRow = getRow(node.getDefName().getResultSheet(), startPoint[0] + i);
//            resultRow.setCustomFormat(true);
//            List<CellReference> currentRowResultCells = copyCells(
//                    startPoint[1], node, resultRow, templateCells);
//            resultCells.addAll(currentRowResultCells);
//            applyRowSettings(templateRow, resultRow, node.getDefName());
//        }
//        return resultCells;
//    }
//
//    List<CellReference> copyCells(int hOffset, DomNode node, Row row, List<Cell> templateCells) {
//        List<CellReference> resultCells = new ArrayList<>();
//        Range range = node.getDefName().getRange();
//        for (Cell templateCell : templateCells) {
//            Cell newCell = XmlUtils.deepCopy(templateCell, Context.jcSML);
//            if (newCell.getF() != null) {
//                addFormulaForPostProcessing(node.getDefName(), row, templateCell, newCell);
//            }
//            CellReference newRef = new CellReference(range.getSheet(), newCell.getR());
//            resultCells.add(newRef);
//            newRef.move(row.getR().intValue(), newRef.getColumn());
//            newRef.shift(0, hOffset);
//            newCell.setR(newRef.toReference());
//            newCell = context.getFromCache(newRef, newCell);
//            newCell.setS(templateCell.getS());
//            if (context.addToCache(newRef, newCell)) {
//                newCell.setParent(row);
//                applyCell(range.getSheet(), row, newRef, newCell);
//                if (node.isEmpty()) {
//                    newCell.setT(null);
//                    newCell.setV(null);
//                    if (BandOrientation.VERTICAL.equals(node.getBandData().getOrientation())) break;
//                } else {
//                    updateCell(node.getBandData(), templateCell, newCell);
//                }
//            } else {
//                if (!node.isEmpty()) {
//                    updateCell(node.getBandData(), templateCell, newCell);
//                }
//            }
//        }
//        return resultCells;
//    }
//
//    private void applyCell(String sheet, Row row, CellReference newRef, Cell cell) {
//        if (row == null) return;
//        row.getC().add(cell);
//        row.getC().sort(CELL_COMPARATOR);
//        CellReference checkRef = newRef;
//        while (checkRef.getColumn() >= 2) {
//            checkRef = new CellReference(sheet, newRef.getRow(), checkRef.getColumn() - 1);
//            Cell prev = new Cell();
//            prev.setR(checkRef.toReference());
//            prev.setParent(row);
//            if (context.getFromCache(checkRef) != null) break;
//            context.addToCache(checkRef, prev);
//            row.getC().add(prev);
//        }
//    }
//
//    Row getRow(Worksheet resultSheet, Integer index) {
//        List<Row> resultSheetRows = resultSheet.getSheetData().getRow();
//        while (resultSheetRows.size() <= index) {
//            Row row = Context.getsmlObjectFactory().createRow();
//            row.setR((long) index + 1);
//            row.setParent(resultSheet.getSheetData());
//            resultSheetRows.add(row);
//        }
//        return resultSheetRows.get(index);
//    }
//
//    void applyRowSettings(Row templateRow, Row newRow, DefNameHolder holder) {
//        newRow.setHt(templateRow.getHt());
//        newRow.setCustomHeight(true);
//        CTPageBreak rowBreaks = holder.getTemplateSheet().getRowBreaks();
//        if (rowBreaks != null && rowBreaks.getBrk() != null) {
//            for (CTBreak templateBreak : rowBreaks.getBrk()) {
//                if (templateRow.getR().equals(templateBreak.getId())) {
//                    CTBreak newBreak = XmlUtils.deepCopy(templateBreak, Context.jcSML);
//                    newBreak.setId(newRow.getR());
//                    holder.getResultSheet().getRowBreaks().getBrk().add(newBreak);
//                }
//            }
//        }
//    }
//
//    void addFormulaForPostProcessing(DefNameHolder holder, Row newRow, Cell templateCell, Cell newCell) {
//        Worksheet worksheet = holder.getResultSheet();
//        Set<Range> formulaRanges = Range.fromCellFormula(context.getResult().getSheetName(worksheet), templateCell);
////        if (holder.getRange().containsAny(formulaRanges)) {
////            //innerFormulas.add(newCell);
////        } else {
////            //outerFormulas.add(newCell);
////        }
//    }
//
//    void updateCell(BandData band, Cell template, Cell newCell) {
//        String cellValue = context.getTemplate().getCellValue(template);
//        if (StringUtils.isEmpty(cellValue)) {
//            newCell.setV(null);
//            return;
//        }
//        if (UNIVERSAL_ALIAS_PATTERN.matcher(cellValue).matches()) {
//            String parameterName = unwrapParameterName(cellValue);
//            String fullParameterName = band.getName() + "." + parameterName;
//            Object value = band.getData().get(parameterName);
//
//            if (value == null) {
//                newCell.setT(null);
//                newCell.setV(null);
//                return;
//            }
//
//            String formatString = getFormatString(parameterName, fullParameterName);
//            if (formatString != null) {
//                newCell.setT(STCellType.S);
//                newCell.setV(context.addSharedString(formatValue(value, parameterName, fullParameterName)));
//            } else if (value instanceof Boolean) {
//                newCell.setT(STCellType.B);
//                newCell.setV(String.valueOf(value));
//            } else if (value instanceof Number) {
//                newCell.setT(STCellType.N);
//                newCell.setV(String.valueOf(value));
//            } else if (value instanceof Date) {
//                newCell.setT(STCellType.N);
//                newCell.setV(String.valueOf(HSSFDateUtil.getExcelDate((Date) value)));
//            } else {
//                newCell.setT(STCellType.S);
//                newCell.setV(context.addSharedString(formatValue(value, parameterName, fullParameterName)));
//            }
//        } else {
//            newCell.setT(STCellType.S);
//            newCell.setV(context.addSharedString(insertBandDataToString(band, cellValue)));
//        }
//    }
//
//    void processColumnWidths() {
//        context.getResult().getWorksheets().forEach(w -> {
//            try {
//                List<Row> rows = w.getWorksheet().getContents().getSheetData().getRow();
//                CTPageBreak rowBreaks = w.getWorksheet().getContents().getRowBreaks();
//                if (rowBreaks != null) {
//                    rowBreaks.getBrk().sort(Comparator.comparingLong(CTBreak::getId));
//                }
//                rows.forEach(row -> row.getC().sort(CELL_COMPARATOR));
//                rows.sort(Comparator.comparing(Row::getR));
//                w.getWorksheet().getContents().getCols().get(0).getCol().clear();
//
//            } catch (Docx4JException e) {
//                throw new RuntimeException(e.getMessage(), e);
//            }
//        });
//        context.sheetColumnModelMap.forEach((k, v) -> {
//            Worksheet worksheet = context.getResult().getSheetByName(k.getSheet());
//            Map<Integer, ColumnModelHolder> modelMap = v;
//            ColumnModelHolder model = modelMap.values().stream().sorted((e1, e2) ->
//                    Integer.compare(e2.getUsageCount(), e1.getUsageCount()))
//                    .findFirst().get();
//            Cols parent = worksheet.getCols().get(0);
//            model.getColumn().setParent(parent);
//            List<Col> sheetCols = parent.getCol();
//            Col last = sheetCols.isEmpty() ? null : sheetCols.get(sheetCols.size() - 1);
//            if (last == null || Math.abs(
//                    ObjectUtils.defaultIfNull(model.getColumn().getWidth(), DEF_COL_WIDTH)
//                            - ObjectUtils.defaultIfNull(last.getWidth(), DEF_COL_WIDTH)
//            ) > 0.01) {
//                if (needEmptyCols(last, model.getColumn())) {
//                    Col emptyCol = new Col();
//                    emptyCol.setMin(last.getMax() + 1);
//                    emptyCol.setMax(model.getColumn().getMin() - 1);
//                    emptyCol.setCustomWidth(true);
//                    emptyCol.setWidth(DEF_COL_WIDTH);
//                    parent.getCol().add(emptyCol);
//                }
//                parent.getCol().add(model.getColumn());
//            } else {
//                last.setMax(model.getColumn().getMin());
//            }
//        });
//
//        context.getResult().getWorksheets().forEach(w -> {
//            try {
//                Worksheet worksheet = w.getWorksheet().getContents();
//                Cols parent = worksheet.getCols().get(0);
//                parent.getCol().sort((c1, c2) -> {
//                    int dc = Long.compare(c1.getMin(), c2.getMin());
//                    if (dc == 0) {
//                        dc = Long.compare(c1.getMax(), c2.getMax());
//                    }
//                    return dc;
//                });
//                Col lastCol = new Col();
//                lastCol.setWidth(DEF_COL_WIDTH);
//                lastCol.setMin(parent.getCol().get(parent.getCol().size() - 1).getMax() + 1);
//                lastCol.setMax(1025);
//                parent.getCol().add(lastCol);
//
//            } catch (Docx4JException e) {
//                throw new RuntimeException(e.getMessage(), e);
//            }
//        });
//    }
//
//    private boolean needEmptyCols(Col last, Col column) {
//        return last != null && column.getMin() - last.getMax() > 1;
//    }
//
//    protected void updateMergeRegions() {
//        context.defNameRegistryMap.values().forEach(holder -> {
//            Worksheet resultSheet = holder.getResultSheet();
//            Worksheet templateSheet = holder.getTemplateSheet();
//            CTMergeCells resultMergeCells = resultSheet.getMergeCells();
//            if (resultMergeCells == null) {
//                resultMergeCells = new CTMergeCells();
//                resultMergeCells.setParent(resultSheet);
//                resultSheet.setMergeCells(resultMergeCells);
//            }
//
//            holder.getResultRanges().forEach(range -> {
//                if (templateSheet.getMergeCells() != null && templateSheet.getMergeCells().getMergeCell() != null) {
//                    for (CTMergeCell templateMergeRegion : templateSheet.getMergeCells().getMergeCell()) {
//                        Range mergeRange = Range.fromRange(holder.getRange().getSheet(), templateMergeRegion.getRef());
//                        if (holder.getRange().intersects(mergeRange)) {
//                            Range resultMergeRange = mergeRange.copy().shift(
//                                    range.getFirstRow() - holder.getRange().getFirstRow(),
//                                    range.getFirstColumn() - holder.getRange().getFirstColumn()
//                            );
//                            CTMergeCell resultMergeRegion = new CTMergeCell();
//                            resultMergeRegion.setRef(resultMergeRange.toRange());
//                            resultMergeRegion.setParent(resultSheet.getMergeCells());
//                            resultSheet.getMergeCells().getMergeCell().add(resultMergeRegion);
//                        }
//                    }
//                }
//                Collection<Range> forMerge = holder.getMergeBands().get(range);
//                if (forMerge == null || forMerge.isEmpty()) return;
//                List<Range> ranges = forMerge.stream().sorted(Comparator.comparingInt(Range::getFirstColumn))
//                        .collect(Collectors.toList());
//                if (range.getFirstColumn() == ranges.get(0).getFirstColumn()) {
//                    //horizontal merge
//                    CTMergeCell resultMergeRegion = new CTMergeCell();
//                    resultMergeRegion.setRef(new Range(
//                            holder.getRange().getSheet(),
//                            ranges.get(0).getFirstColumn(),
//                            holder.getRange().getFirstRow(),
//                            ranges.get(ranges.size() - 1).getLastColumn(),
//                            holder.getRange().getFirstRow()
//                    ).toRange());
//                    resultMergeRegion.setParent(resultSheet.getMergeCells());
//                    resultSheet.getMergeCells().getMergeCell().add(resultMergeRegion);
//                } else {
//                    //vertical merge
//                    for (int i = holder.getRange().getFirstColumn(); i <= holder.getRange().getLastColumn(); i++) {
//                        CTMergeCell resultMergeRegion = new CTMergeCell();
//                        resultMergeRegion.setRef(new Range(
//                                holder.getRange().getSheet(),
//                                i,
//                                ranges.get(0).getFirstRow(),
//                                i,
//                                ranges.get(ranges.size() - 1).getLastRow()
//                        ).toRange());
//                        resultMergeRegion.setParent(resultSheet.getMergeCells());
//                        resultSheet.getMergeCells().getMergeCell().add(resultMergeRegion);
//                    }
//                }
//            });
//            resultSheet.getMergeCells().setCount((long) resultSheet.getMergeCells().getMergeCell().size());
//        });
//        context.getResult().getWorksheets().forEach(w -> {
//            try {
//                if (w.getWorksheet().getContents().getMergeCells().getCount() < 1) {
//                    w.getWorksheet().getContents().setMergeCells(null);
//                } else {
//                    w.getWorksheet().getContents().getMergeCells().getMergeCell()
//                            .sort(Comparator.comparing(CTMergeCell::getRef));
//                }
//            } catch (Docx4JException e) {
//                throw new RuntimeException(e.getMessage(), e);
//            }
//        });
//    }
//
//    protected void updateCharts() {
//        context.getResult().getChartSpaces().entrySet().forEach(entry -> {
//            context.defNameRegistryMap.values().forEach(holder -> {
//                Range templateRange = holder.getRange();
//                if (templateRange.intersects(entry.getKey())) {
//                    List<Range> chartBandResultRanges = holder.getResultRanges();
//                    if (!chartBandResultRanges.isEmpty()) {
//                        Range firstResultRange = chartBandResultRanges.get(0);
//                        shiftChart(entry.getValue(), templateRange, firstResultRange);
//                        CTChart chart = entry.getValue().getChartSpace().getChart();
//                        CTPlotArea plotArea = chart.getPlotArea();
//                        List<Object> areaChartOrArea3DChartOrLineChart =
//                                plotArea.getAreaChartOrArea3DChartOrLineChart();
//                        areaChartOrArea3DChartOrLineChart.stream().forEach(o -> {
//                            if (o instanceof ListSer) {
//                                processSeries((ListSer) o);
//                            }
//                        });
//                    }
//                }
//            });
//        });
//    }
//
//    protected void shiftChart(Document.ChartWrapper chart, Range templateRange, Range firstResultRange) {
//        int downOffset = firstResultRange.getFirstRow() - templateRange.getFirstRow();
//        int rightOffset = firstResultRange.getFirstColumn() - templateRange.getFirstColumn();
//        CTTwoCellAnchor anchor = chart.getAnchor();
//        anchor.getFrom().setRow(anchor.getFrom().getRow() + downOffset);
//        anchor.getFrom().setCol(anchor.getFrom().getCol() + rightOffset);
//        anchor.getTo().setRow(anchor.getTo().getRow() + downOffset);
//        anchor.getTo().setCol(anchor.getTo().getCol() + rightOffset);
//    }
//
//    private void processSeries(ListSer o) {
//        List<SerContent> ser = o.getSer();
//        ser.forEach(ctBarSer -> {
//            CTAxDataSource captions = ctBarSer.getCat();
//            CTNumDataSource data = ctBarSer.getVal();
//            Range temlpateCaptionsRange;
//            if (captions != null && captions.getStrRef() != null) {
//                temlpateCaptionsRange = Range.fromFormula(captions.getStrRef().getF());
//            } else {
//                temlpateCaptionsRange = null;
//            }
//            Range templateDataRange;
//            if (data != null && data.getNumRef() != null) {
//                templateDataRange = Range.fromFormula(data.getNumRef().getF());
//            } else {
//                templateDataRange = null;
//            }
//
//            Boolean temlpateCaptionsFinded = false, templateDataFinded = false;
//            for (DefNameHolder holder : context.defNameRegistryMap.values()) {
//                List<Range> seriesResultRanges = holder.getResultRanges();
//                if (seriesResultRanges.isEmpty()) return;
//                Range seriesFirstRange = seriesResultRanges.get(0);
//                Range seriesLastRange = seriesResultRanges.get(seriesResultRanges.size() - 1);
//                if (!temlpateCaptionsFinded && temlpateCaptionsRange != null && holder.getRange().contains(temlpateCaptionsRange)) {
//                    temlpateCaptionsRange.shift(
//                            seriesFirstRange.getFirstRow() - holder.getRange().getFirstRow(),
//                            seriesFirstRange.getFirstColumn() - holder.getRange().getFirstColumn()
//                    );
//                    temlpateCaptionsRange.grow(
//                            seriesLastRange.getFirstRow() - seriesFirstRange.getFirstRow(),
//                            seriesLastRange.getFirstColumn() - seriesFirstRange.getFirstColumn()
//                    );
//                    captions.getStrRef().setF(temlpateCaptionsRange.toFormula());
//                    temlpateCaptionsFinded = true;
//                }
//                if (!templateDataFinded && templateDataRange != null && holder.getRange().contains(templateDataRange)) {
//                    templateDataRange.shift(
//                            seriesFirstRange.getFirstRow() - holder.getRange().getFirstRow(),
//                            seriesFirstRange.getFirstColumn() - holder.getRange().getFirstColumn()
//                    );
//
//                    templateDataRange.grow(
//                            seriesLastRange.getFirstRow() - seriesFirstRange.getFirstRow(),
//                            seriesLastRange.getFirstColumn() - seriesFirstRange.getFirstColumn()
//                    );
//                    data.getNumRef().setF(templateDataRange.toFormula());
//                    templateDataFinded = true;
//                }
//                if (temlpateCaptionsFinded && templateDataFinded) break;
//            }
//        });
//    }
//
//    protected void updateConditionalFormatting() {
//        context.getResult().getWorksheets().forEach(sheetWrapper -> {
//            try {
//                Worksheet worksheet = sheetWrapper.getWorksheet().getContents();
//                worksheet.getConditionalFormatting().forEach(ctConditionalFormatting -> {
//                    List<String> references = new ArrayList<String>();
//                    ctConditionalFormatting.getSqref().forEach(ref -> {
//                        Range formulaRange = Range.fromRange(sheetWrapper.getName(), ref);
//                        context.defNameRegistryMap.values().forEach(holder -> {
//                            if (holder.getRange().contains(formulaRange)) {
//                                List<Range> resultRanges = holder.getResultRanges();
//                                for (Range resultRange : resultRanges) {
//                                    Range shift = formulaRange.copy().shift(
//                                            resultRange.getFirstRow() - holder.getRange().getFirstRow(),
//                                            resultRange.getFirstColumn() - holder.getRange().getFirstColumn()
//                                    );
//                                    references.add(shift.toRange());
//                                }
//                            }
//                        });
//                    });
//                    ctConditionalFormatting.getSqref().clear();
//                    ctConditionalFormatting.getSqref().addAll(references);
//                });
//            } catch (Docx4JException e) {
//                logger.warn(e.getMessage(), e);
//            }
//        });
//    }
//
//    protected void saveAndClose() {
//        try {
//            SaveToZipFile saver = new SaveToZipFile(context.getResult().getPackage());
//            if (ReportOutputType.xlsx.equals(reportTemplate.getOutputType())) {
//                saver.save(outputStream);
//                outputStream.flush();
//            } else if (ReportOutputType.pdf.equals(reportTemplate.getOutputType())) {
//                if (htmlToPdfConverter != null) {
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    saver.save(bos);
//                    htmlToPdfConverter.convert("s", outputStream);
//                    outputStream.flush();
//                } else {
//                    throw new UnsupportedOperationException(
//                            String.format(
//                                    "XlsxFormatter could not convert result to pdf without Libre/Open office connected. " +
//                                            "Please setup Libre/Open office connection details."));
//                }
//            } else {
//                throw new UnsupportedOperationException(
//                        String.format("XlsxFormatter could not output file with type [%s]",
//                                reportTemplate.getOutputType()));
//            }
//        } catch (Docx4JException e) {
//            throw wrapWithReportingException("An error occurred while saving result report", e);
//        } catch (IOException e) {
//            throw wrapWithReportingException("An error occurred while saving result report to PDF", e);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            IOUtils.closeQuietly(outputStream);
//        }
//    }
//
//    static class DomContext {
//        Map<String, CTSheetDimension> dimensionMap = new HashMap<>();
//        Map<String, DefNameHolder> defNameRegistryMap = new HashMap<>();
//        Document template;
//        Document result;
//        CTSst sharedValues;
//        Map<String, Map<String, Cell>> cellReferenceCache = new HashMap<>();
//        Map<SheetColumn, Map<Integer, ColumnModelHolder>> sheetColumnModelMap = new TreeMap<>();
//        List<DomNode> nodeList = new ArrayList<>();
//        AtomicLong idSeq = new AtomicLong();
//
//        protected DomContext(Document template) throws Docx4JException {
//            this.template = template;
//            this.result = Document.create((SpreadsheetMLPackage) template.getPackage().clone());
//            result.clearWorkbook();
//            result.getWorkbook().getCalcPr().setCalcMode(STCalcMode.AUTO);
//            result.getWorkbook().getCalcPr().setFullCalcOnLoad(true);
//            result.getWorkbook().setDefinedNames(new DefinedNames());
//            if (result.getWorkbook().getBookViews() == null) {
//                result.getWorkbook().setBookViews(new BookViews());
//            }
//            BookViews views = result.getWorkbook().getBookViews();
//            if (views.getWorkbookView().isEmpty()) {
//                CTBookView defaultView = new CTBookView();
//                views.getWorkbookView().add(defaultView);
//            }
//            Parts parts = result.getPackage().getParts();
//            SharedStrings sharedStrings = (SharedStrings) parts.get(new PartName("/xl/sharedStrings.xml"));
//            sharedValues = sharedStrings.getContents();
//            sharedValues.getSi().clear();
//            sheetColumnModelMap.clear();
//            DefinedNames definedNames = template.getWorkbook().getDefinedNames();
//            if (definedNames != null) {
//                definedNames.getDefinedName().forEach(this::registryName);
//            }
//            result.getWorksheets().forEach(w -> {
//                CTSheetDimension dimension = new CTSheetDimension();
//                try {
//                    dimension.setParent(w.getWorksheet().getContents());
//                    w.getWorksheet().getContents().setDimension(dimension);
//                    dimensionMap.putIfAbsent(w.getName(), dimension);
//                } catch (Docx4JException e) {
//                    throw new RuntimeException(e.getMessage(), e);
//                }
//            });
//        }
//
//        private void registryName(CTDefinedName name) {
//            defNameRegistryMap.put(name.getName(), new DefNameHolder(
//                    name,
//                    template, result
//            ));
//        }
//
//        public List<DomNode> getNodeList() {
//            return nodeList;
//        }
//
//
//        public void updateDimensions() {
//            result.getWorksheets().forEach(w -> {
//                try {
//                    Worksheet worksheet = w.getWorksheet().getContents();
//                    CTSheetDimension dimension = dimensionMap.get(w.getName());
//                    dimension.setRef(Range.fromCells(w.getName(), "A1", new CellReference(
//                            w.getName(),
//                            worksheet.getSheetData().getRow().size(),
//                            sheetColumnModelMap.keySet().stream().filter(e -> e.getSheet().equals(w.getName()))
//                                    .max(Comparator.comparingInt(SheetColumn::getColumn)).map(SheetColumn::getColumn)
//                                    .orElse(1)
//                    ).toReference()).toRange());
//                } catch (Docx4JException e) {
//                    throw new RuntimeException(e.getMessage(), e);
//                }
//            });
//        }
//
//        public void normalizeNodes() {
//            for (int i = nodeList.size() - 1; i > 0; i--) {
//                DomNode current = nodeList.get(i);
//                boolean left = false;
//                boolean top = false;
//                for (int j = i - 1; j >= 0; j--) {
//                    DomNode prev = nodeList.get(j);
//                    if (!left && current.getRange().getSheet().equals(prev.getRange().getSheet())
//                            && (current.getRange().getFirstColumn() > prev.getRange().getLastColumn()
//                            || (BandOrientation.VERTICAL.equals(current.getBandData().getOrientation())
//                            || !current.getParent().equals(prev.getParent()))
//                            && current.getDefName().equals(prev.getDefName()))) {
//                        current.left = prev;
//                        left = true;
//                    }
//                    if (!top && current.getRange().getSheet().equals(prev.getRange().getSheet()) &&
//                            (current.getRange().getFirstRow() > prev.getRange().getFirstRow()
//                                    || (BandOrientation.HORIZONTAL.equals(current.getBandData().getOrientation())
//                                    || !current.getParent().equals(prev.getParent()))
//                                    && current.getDefName().equals(prev.getDefName()))) {
//                        current.top = prev;
//                        top = true;
//                    }
//                    if (left && top) break;
//                }
//            }
//        }
//
//        Map<Integer, ColumnModelHolder> getColumnModel(SheetColumn column) {
//            Map<Integer, ColumnModelHolder> model = sheetColumnModelMap.getOrDefault(column, new HashMap<>());
//            sheetColumnModelMap.put(column, model);
//            return model;
//        }
//
//        boolean addToCache(CellReference ref, Cell cell) {
//            Map<String, Cell> cellCache =
//                    cellReferenceCache.computeIfAbsent(ref.getSheet(), s -> new HashMap<>());
//            if (cellCache.containsKey(ref.toReference())) return false;
//            cellCache.put(ref.toReference(), cell);
//            cellReferenceCache.put(ref.getSheet(), cellCache);
//            return true;
//        }
//
//        Cell getFromCache(CellReference ref, Cell templateCell) {
//            Map<String, Cell> cellCache =
//                    cellReferenceCache.computeIfAbsent(ref.getSheet(), s -> new HashMap<>());
//            return cellCache.getOrDefault(ref.toReference(), XmlUtils.deepCopy(templateCell, Context.jcSML));
//        }
//
//        Cell getFromCache(CellReference ref) {
//            Map<String, Cell> cellCache =
//                    cellReferenceCache.computeIfAbsent(ref.getSheet(), s -> new HashMap<>());
//            return cellCache.get(ref.toReference());
//        }
//
//        DefNameHolder getName(String name) {
//            return defNameRegistryMap.get(name);
//        }
//
//        Document getTemplate() {
//            return template;
//        }
//
//        Document getResult() {
//            return result;
//        }
//
//        CTSst getSharedStrings() {
//            return sharedValues;
//        }
//
//        String addSharedString(String value) {
//            CTRst crt = new CTRst();
//            CTXstringWhitespace ctXstringWhitespace = new CTXstringWhitespace();
//            ctXstringWhitespace.setSpace("preserve");
//            ctXstringWhitespace.setValue(value);
//            ctXstringWhitespace.setParent(crt);
//            crt.setT(ctXstringWhitespace);
//            sharedValues.getSi().add(crt);
//            sharedValues.setUniqueCount((long) sharedValues.getSi().size());
//            return String.valueOf(sharedValues.getSi().size() - 1);
//        }
//    }
//
//    final static class DomNode {
//        Long id;
//        DomContext ctx;
//        DefNameHolder defNameHolder;
//        DomNode parent;
//        DomNode prevChild;
//        DomNode left;
//        DomNode top;
//        Range range;
//        Multimap<DefNameHolder, DomNode> childs = LinkedHashMultimap.create();
//        Boolean empty = false;
//        BandData bandData;
//        Integer mergeCellsSize = 0;
//        int width = 0;
//        int height = 0;
//
//        private DomNode(DomContext ctx, BandData bandData, DefNameHolder defNameHolder) {
//            this.ctx = ctx;
//            this.id = ctx.idSeq.incrementAndGet();
//            this.bandData = bandData;
//            this.defNameHolder = defNameHolder;
//            this.range = defNameHolder == null ? null : defNameHolder.getRange().copy();
//        }
//
//        static DomNode make(DomContext ctx, BandData bandData) {
//            DomNode node = new DomNode(ctx, bandData, ctx.getName(bandData.getName()));
//            if (node.getRange() != null) {
//                ctx.getNodeList().add(node);
//            }
//            bandData.getChildrenBands().forEach((name, bands) -> {
//                List<DomNode> childs = new ArrayList<>(bands.size());
//                DefNameHolder holder = ctx.getName(name);
//                bands.forEach(band -> {
//                    DomNode child = make(ctx, band);
//                    child.parent = node;
//                    child.prevChild = childs.isEmpty() ? null : childs.get(childs.size() - 1);
//                    node.childs.put(holder, child);
//                });
//            });
//            return node;
//        }
//
//        static DomNode emptyNode(DomNode dataNode) {
//            DomNode node = make(dataNode.ctx,
//                    dataNode.getBandData()
//            );
//            node.empty = true;
//            node.parent = dataNode.parent;
//            return node;
//        }
//
//        Long getId() {
//            return id;
//        }
//
//        Boolean horizontalMergeCheck() {
//            return !childs.isEmpty() &&
//                    BandOrientation.VERTICAL.equals(bandData.getOrientation()) &&
//                    childs.asMap().entrySet().stream().filter((e) -> e.getValue().size() > 1).count() > 0;
//        }
//
//        Boolean verticalMergeCheck() {
//            return !childs.isEmpty() &&
//                    BandOrientation.HORIZONTAL.equals(bandData.getOrientation()) &&
//                    childs.asMap().entrySet().stream().filter((e) -> e.getValue().size() > 1 &&
//                            BandOrientation.HORIZONTAL.equals((e.getValue().stream().findFirst()
//                                    .get().getBandData().getOrientation()))).count() > 0;
//        }
//
//        void setMergeSize(Integer mergeCells) {
//            this.mergeCellsSize = mergeCells;
//        }
//
//        Integer getMergeSize() {
//            return mergeCellsSize;
//        }
//
//        BandData getBandData() {
//            return bandData;
//        }
//
//        DefNameHolder getDefName() {
//            return defNameHolder;
//        }
//
//        DefNameHolder getChilDefName() {
//            return childs.keySet().isEmpty() ? null : childs.keySet().stream().findFirst().get();
//        }
//
//        Multimap<DefNameHolder, DomNode> getChildes() {
//            return childs;
//        }
//
//        DomNode getParent() {
//            return parent;
//        }
//
//        DomNode getPrevChild() {
//            return prevChild;
//        }
//
//        DomNode getLeft() {
//            return left;
//        }
//
//        DomNode getTop() {
//            return top;
//        }
//
//        Boolean isEmpty() {
//            return empty;
//        }
//
//        Range getRange() {
//            return range;
//        }
//
//        void setRange(Range range) {
//            this.range = range;
//            updateRange();
//        }
//
//        void addRange(Range range) {
//            if (this.range == null) {
//                this.range = range.copy();
//            } else {
//                this.range.grow(
//                        Math.max(this.range.getLastRow(), range.getLastRow()) - this.range.getLastRow(),
//                        Math.max(this.range.getLastColumn(), range.getLastColumn()) - this.range.getLastColumn()
//                );
//            }
//            updateRange();
//        }
//
//        void updateRange() {
//            if (range != null) {
//                width = range.getLastColumn() - range.getFirstColumn() + 1;
//                height = range.getLastRow() - range.getFirstRow() + 1;
//            }
//        }
//
//        int getWidth() {
//            return width;
//        }
//
//        int getHeight() {
//            return height;
//        }
//
//        int verticalOffset() {
//            int offset = top != null ? top.getRange().getLastRow() - top.getDefName().getRange().getLastRow() : 0;
//            if (top != null && !parent.equals(top.getParent()) && BandOrientation.VERTICAL.equals(bandData.getOrientation())) {
//                offset = Math.max(parent.getRange().getLastRow() - parent.getDefName().getRange().getLastRow(), offset);
//            }
//            if (offset < 0) {
//                offset = 0;
//            }
//            return offset;
//        }
//
//        int horizontalOffset() {
//            int offset = left != null ? left.getRange().getLastColumn() - left.getDefName().getRange().getLastColumn() : 0;
//            if (offset < 0) {
//                offset = 0;
//            }
//            return offset;
//        }
//
//        int getHStep() {
//            return left == null || !left.getDefName().equals(defNameHolder) ? 0 : defNameHolder.getWidth();
//        }
//
//        int getVStep() {
//            return top == null || !top.getDefName().equals(defNameHolder) ? 0 : defNameHolder.getHeight();
//        }
//    }
//
//    final static class DefNameHolder {
//        CTDefinedName name;
//        Range range;
//        Range resultRange;
//        List<Range> resultRanges = new ArrayList<>();
//        Worksheet resultSheet;
//        Worksheet templateSheet;
//        Multimap<Range, Range> mergeBands = LinkedHashMultimap.create();
//        List<Col> columnsModel = new ArrayList<>(1000);
//        int width;
//        int height;
//
//        private DefNameHolder(CTDefinedName name, Document template, Document result) {
//            this.name = name;
//            this.range = Range.fromFormula(name.getValue());
//            resultSheet = result.getSheetByName(range.getSheet());
//            templateSheet = template.getSheetByName(range.getSheet());
//            createColumnModel(template);
//            this.width = range.getLastColumn() - range.getFirstColumn() + 1;
//            this.height = range.getLastRow() - range.getFirstRow() + 1;
//        }
//
//        private void createColumnModel(Document template) {
//            columnsModel = new ArrayList<>(range.getLastColumn() - range.getFirstColumn() + 1);
//            range.toCellReferences().forEach(ref -> {
//                Col model = template.getColumnForCell(ref.getSheet(), ref);
//                if (model != null) {
//                    columnsModel.add(ref.getColumn() - range.getFirstColumn(), model);
//                } else {
//                    logger.warn("Template Column doesn't have column model");
//                    Worksheet sheet = template.getSheetByName(ref.getSheet());
//                    List<Cols> colsList = sheet.getCols();
//                    List<Col> cols = colsList.get(0).getCol();
//                    Col tplCol = XmlUtils.deepCopy(cols.get(0), Context.jcSML);
//                    tplCol.setMin(ref.getColumn());
//                    tplCol.setMax(ref.getColumn());
//                    cols.add(tplCol);
//                }
//            });
//        }
//
//        Col generateColumn(int firstColumn, int column) {
//            Col col = columnsModel.get((column - firstColumn) % columnsModel.size());
//            Col result = XmlUtils.deepCopy(col, Context.jcSML);
//            result.setMin(column);
//            result.setMax(column);
//            return result;
//        }
//
//        CTDefinedName getName() {
//            return name;
//        }
//
//        Range getRange() {
//            return range;
//        }
//
//        Worksheet getResultSheet() {
//            return resultSheet;
//        }
//
//        Worksheet getTemplateSheet() {
//            return templateSheet;
//        }
//
//        Range getResultRange() {
//            return resultRange;
//        }
//
//        void updateResultRange(Range range) {
//            if (this.resultRange == null) {
//                this.resultRange = range.copy();
//            } else {
//                this.resultRange.grow(
//                        Math.max(resultRange.getLastRow(), range.getLastRow()) - resultRange.getLastRow(),
//                        Math.max(resultRange.getLastColumn(), range.getLastColumn()) - resultRange.getLastColumn()
//                );
//            }
//            resultRanges.add(range);
//        }
//
//        Multimap<Range, Range> getMergeBands() {
//            return mergeBands;
//        }
//
//        int getWidth() {
//            return width;
//        }
//
//        int getHeight() {
//            return height;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//
//            DefNameHolder that = (DefNameHolder) o;
//
//            return name != null ? name.getName().equals(that.name.getName()) : that.name == null;
//
//        }
//
//        @Override
//        public int hashCode() {
//            return name != null ? name.getName().hashCode() : 0;
//        }
//
//        @Override
//        public String toString() {
//            return "DefNameHolder{" +
//                    "name=" + name.getName() +
//                    '}';
//        }
//
//        List<Range> getResultRanges() {
//            return resultRanges;
//        }
//    }
//
//    static class SheetColumn implements Comparable<SheetColumn> {
//        String sheet;
//        Integer column;
//
//        SheetColumn(String sheet, Integer column) {
//            this.sheet = sheet;
//            this.column = column;
//        }
//
//        String getSheet() {
//            return sheet;
//        }
//
//        Integer getColumn() {
//            return column;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//
//            SheetColumn that = (SheetColumn) o;
//
//            if (sheet != null ? !sheet.equals(that.sheet) : that.sheet != null) return false;
//            return column != null ? column.equals(that.column) : that.column == null;
//        }
//
//        @Override
//        public int hashCode() {
//            int result = sheet != null ? sheet.hashCode() : 0;
//            result = 31 * result + (column != null ? column.hashCode() : 0);
//            return result;
//        }
//
//        @Override
//        public String toString() {
//            return "SheetColumn{" +
//                    "sheet='" + sheet + '\'' +
//                    ", column=" + column +
//                    '}';
//        }
//
//        @Override
//        public int compareTo(SheetColumn o) {
//            if (o == null) return -1;
//            int dx = sheet.compareTo(o.sheet);
//            if (dx == 0) {
//                dx = Integer.compare(column, o.column);
//            }
//            return dx;
//        }
//    }
//
//    static class ColumnModelHolder {
//        Col column;
//        int usageCount;
//
//        ColumnModelHolder(Col column) {
//            this.column = column;
//        }
//
//        Col getColumn() {
//            return column;
//        }
//
//        int getUsageCount() {
//            return usageCount;
//        }
//
//        void incrementUsage() {
//            usageCount++;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//
//            ColumnModelHolder that = (ColumnModelHolder) o;
//
//            return column != null ? column.getWidth().intValue() == that.column.getWidth().intValue() : that.column == null;
//        }
//
//        @Override
//        public int hashCode() {
//            return column != null ? column.hashCode() : 0;
//        }
//    }
//
//}
