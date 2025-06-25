import 'package:flutter/material.dart';

void main() {
  runApp(const NucleusPassApp());
}

class NucleusPassApp extends StatelessWidget {
  const NucleusPassApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Nucleus Pass',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const HomeScreen(),
    );
  }
}

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Nucleus Pass'),
      ),
      body: const Center(
        child: Text('Welcome to Nucleus Pass!'),
      ),
    );
  }
}


