package cn.rpamis.common.dto.enums;

/**
 * @author benym
 * @date 2022/7/10 20:37
 */
public class Trace {

    private static final long serialVersionUID = 1L;

    public static final String TRACE_ID = "X-B3-TraceId";

    public static final String SPAN_ID = "X-B3-SpanId";

    private String traceId;

    private String spanId;

    public Trace() {
    }

    public Trace(String traceId, String spanId) {
        this.traceId = traceId;
        this.spanId = spanId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }
}
