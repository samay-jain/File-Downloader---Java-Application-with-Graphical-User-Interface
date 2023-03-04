import java.awt.EventQueue;

import javax.swing.JFrame;
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JProgressBar;
import java.awt.SystemColor;

public class ui {
	public static JLabel showprogress;
	public static JProgressBar progressBar;
	public static void download(String link,String filename)
	{
		Runnable newthread  = new Runnable() {
			public void run() {
			try {
				String newfilename = "C:\\Users\\Admin\\Downloads\\"+filename;
				URL url = new URL(link);
				HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
                long completeFileSize = httpConnection.getContentLength();
				BufferedInputStream inputstream = new BufferedInputStream(url.openStream());
				FileOutputStream outputstream = new FileOutputStream(newfilename);
				BufferedOutputStream bot = new BufferedOutputStream(outputstream,1024);
				byte[] buffer = new byte[1024];
				int length=0;
				long downloadedsize=0;
				progressBar.setMaximum(100000);
				showprogress.setText("Your file is being downloaded please wait.");
				progressBar.setVisible(true);
				while((length=inputstream.read(buffer,0,1024))!=-1)
				{
					downloadedsize+=length;
					final int currentProgress = (int) ((((double)downloadedsize) / ((double)completeFileSize)) * 100000d);
					SwingUtilities.invokeLater(new Runnable() {
						public void run()
						{
							progressBar.setValue(currentProgress);
						}
					});
					bot.write(buffer,0,length);
				}
				showprogress.setText("Download Finished!");
				inputstream.close();
				bot.close();
				outputstream.close();
				JOptionPane.showMessageDialog(null, "File is Downloaded successfully!");
				}
				catch(Exception e)
				{
					showprogress.setText("Downloading Failed!");
					e.printStackTrace();
				}
				}
			};
			new Thread(newthread).start();
	}

	private JFrame frame;
	private JTextField geturl;
	private JTextField getfilename;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ui window = new ui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(204, 204, 255));
		frame.setResizable(false);
		frame.setBounds(100, 100, 1198, 693);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("File Downloader");
		lblNewLabel.setForeground(new Color(0, 0, 204));
		lblNewLabel.setBackground(new Color(255, 51, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(423, 33, 340, 52);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Download Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(163, 121, 870, 223);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblEnterUrl = new JLabel("Enter URL");
		lblEnterUrl.setBounds(36, 41, 142, 34);
		lblEnterUrl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
		panel.add(lblEnterUrl);
		
		geturl = new JTextField();
		geturl.setBackground(new Color(204, 204, 255));
		geturl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		geturl.setBounds(205, 41, 596, 34);
		panel.add(geturl);
		geturl.setColumns(10);
		
		JLabel lblSaveFileAs = new JLabel("Save file as");
		lblSaveFileAs.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
		lblSaveFileAs.setBounds(36, 144, 155, 34);
		panel.add(lblSaveFileAs);
		
		getfilename = new JTextField();
		getfilename.setBackground(new Color(204, 204, 255));
		getfilename.setToolTipText("Filename with extension (ex filename.jpg)");
		getfilename.setFont(new Font("Tahoma", Font.PLAIN, 20));
		getfilename.setColumns(10);
		getfilename.setBounds(205, 144, 596, 34);
		panel.add(getfilename);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Downloading Progress", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(157, 432, 876, 123);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		showprogress = new JLabel("Downloading progress will be shown here");
		showprogress.setBackground(new Color(204, 153, 255));
		showprogress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		showprogress.setHorizontalAlignment(SwingConstants.CENTER);
		showprogress.setToolTipText("Downloading progress will be shown here");
		showprogress.setBounds(50, 29, 771, 42);
		panel_1.add(showprogress);
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		progressBar.setForeground(new Color(0, 102, 204));
		progressBar.setBackground(new Color(204, 204, 255));
		progressBar.setBounds(285, 93, 319, 20);
		panel_1.add(progressBar);
		
		JButton btnNewButton = new JButton("Downlod");
		btnNewButton.setBackground(new Color(255, 204, 204));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = geturl.getText();
				String filename = getfilename.getText();
				if(url.isEmpty() || filename.isEmpty())
					JOptionPane.showMessageDialog(null, "Please fill all required details!");
				else
				{
					download(url,filename);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(531, 364, 146, 58);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnDownlodAnotherFile = new JButton("Downlod another File");
		btnDownlodAnotherFile.setBackground(new Color(255, 204, 204));
		btnDownlodAnotherFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				geturl.setText("");
				getfilename.setText("");
				showprogress.setText("");
				progressBar.setValue(0);
				progressBar.setVisible(false);
			}
		});
		btnDownlodAnotherFile.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnDownlodAnotherFile.setBounds(480, 588, 258, 33);
		frame.getContentPane().add(btnDownlodAnotherFile);
	}
}
