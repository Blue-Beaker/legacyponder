## 0.1.2
Added `/legacyponder showStructure` command to display structure entries with tab completions  
Added link to JEI category for quick access  
Added homepage button in GUI  
Exposed 'Links' classes to ZenScript API for scripting  
Added client world option for structure rendering, enhancing tile entity handling  
Added localized temporary entry for structure display in command  
Improved crash handling, preventing neighbor updating in dummyWorld  
Fixed potential NullPointerExceptions when getting entries  
Tweaked dummy world neighbor checking

## 0.1.1
Added common workaround for certain TileEntity classes, with AE2 compat included  
Added conventional methods to add multiple ingredients to an entry  
Added render workaround for some special, culled renderers  
Fixed text formatting parameters  
Fixed render context for DrawableItem  
Change PagePopups Structure, to fix chained building  
Reworked history system, now history is kept until overridden, and can be restored with a ingame hotkey or client-side command  
Show title in JEI wrapper  
In DrawableGrid, when max columns is 0, place all items in a line  
Catch exceptions when rendering every block, tile and entity in structure  

## 0.1.0
Initial Release
