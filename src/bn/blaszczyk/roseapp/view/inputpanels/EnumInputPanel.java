package bn.blaszczyk.roseapp.view.inputpanels;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import bn.blaszczyk.roseapp.view.ThemeConstants;

@SuppressWarnings("serial")
public class EnumInputPanel extends JPanel implements InputPanel<Enum<?>>, ThemeConstants {
	
	private final JLabel label = new JLabel();
	private final JComboBox<Enum<?>> comboBox;
	private final Enum<?> defValue;
	
	public EnumInputPanel( String name, Enum<?> defValue )
	{
		this.defValue = defValue;
		setLayout(null);
		label.setText(name);
		label.setBounds(0, 0, PROPERTY_WIDTH, LBL_HEIGHT);
		add(label);

		comboBox = new JComboBox<>(defValue.getClass().getEnumConstants());
		comboBox.setBounds( PROPERTY_WIDTH + H_SPACING , 0, VALUE_WIDTH - 10, LBL_HEIGHT);
		setValue(defValue);
		add(comboBox);
	}
		
	@Override
	public String getName()
	{
		return label.getText();
	}
	
	@Override
	public JPanel getPanel()
	{
		return this;
	}

	@Override
	public boolean hasChanged()
	{
		return !getValue().equals(defValue);
	}

	@Override
	public boolean isInputValid()
	{
		return true;
	}

	@Override
	public Enum<?> getValue()
	{
		return (Enum<?>) comboBox.getSelectedItem();
	}

	@Override
	public void setValue(Enum<?> value)
	{
		comboBox.setSelectedItem(value);
	}

	@Override
	public void setChangeListener(ChangeListener l)
	{
		comboBox.addItemListener( e -> l.stateChanged(new ChangeEvent(this)));
	}

	
}