package pe.bazan.luis.plugins.minecraftplugintemplate.enums;

public enum MessageKey {
    WELCOME_MSG("messages.welcome"),
    CMD_TEST_NO_PLAYER("commands.test.no_player"),
    CMD_TEST_PLAYER("commands.test.player")
    ;

    private final String text;

    /**
     * @param text
     */
    MessageKey(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
