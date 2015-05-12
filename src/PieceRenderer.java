import javax.swing.table.DefaultTableCellRenderer;

public final class PieceRenderer extends DefaultTableCellRenderer {
    public PieceRenderer() {
    }

    public void setValue(Object var1) {
        this.setHorizontalAlignment(0);
        this.setText((String)null);
        String var2 = (String)var1;
        if(var2 != null) {
            String var3 = "<B>" + var2 + "</B>";
            var3 = "<HTML><BIG><BIG>" + var3 + "</BIG></BIG></HTML>";
            this.setText(var3);
        }

    }
}