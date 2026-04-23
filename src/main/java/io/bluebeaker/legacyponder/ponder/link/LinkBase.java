package io.bluebeaker.legacyponder.ponder.link;

import io.bluebeaker.legacyponder.ponder.GuiUnconfusion;

import java.util.List;

public interface LinkBase {
    /** Called when the link is clicked
     * @param button Mouse button that was clicked, 0 for left click, 1 for right click, 2 for middle click
     */
    void onClick(GuiUnconfusion screen, int button);


    /**
     * @return Whether the link is clickable. If false, the link will not respond to clicks or key presses, but will still show a tooltip when hovered.
     */
    default boolean isClickable(){return true;}

    /** Called when a key is pressed while the link is hovered. Should return true if the key press was handled, false otherwise.
     * @param keyCode The key code of the key that was pressed
     */
    default boolean onKeyDown(GuiUnconfusion screen, int keyCode){return false;}

    /**
     * Called when the link is hovered. Should return the tooltip to be displayed, or null if no tooltip should be displayed.
     *
     * @return The tooltip to be displayed, or null if no tooltip should be displayed
     */
    List<String> getTooltip(GuiUnconfusion screen);
}
