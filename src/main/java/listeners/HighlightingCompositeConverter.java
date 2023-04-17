package listeners;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class HighlightingCompositeConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        switch (level.toInt()) {
            case Level.DEBUG_INT:
                return ANSIConstants.BOLD + ";" + ANSIConstants.BLACK_FG;
            case Level.ERROR_INT:
                return ANSIConstants.BOLD + ";" + ANSIConstants.RED_FG;
            case Level.INFO_INT:
                return ANSIConstants.BLACK_FG;
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }
}